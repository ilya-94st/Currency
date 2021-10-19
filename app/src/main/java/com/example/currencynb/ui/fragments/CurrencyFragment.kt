package com.example.currencynb.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.currencynb.R
import com.example.currencynb.adapter.CurrencyAdapter
import com.example.currencynb.base.BaseFragment
import com.example.currencynb.databinding.FragmentCurrencyBinding
import com.example.currencynb.other.Resource
import com.example.currencynb.ui.MainActivity
import com.example.currencynb.ui.ViewModelCurrency
import java.text.SimpleDateFormat
import java.util.*


class CurrencyFragment : BaseFragment<FragmentCurrencyBinding>() {
    lateinit var currencyAdapter: CurrencyAdapter
    lateinit var viewModelCurrency: ViewModelCurrency
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCurrencyBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelCurrency = (activity as MainActivity).viewModelCurrency
        initAdapter()
        time()
        binding.ivSettings.setOnClickListener {
            findNavController().navigate(R.id.action_currencyFragment_to_settingsFragment)
        }
        binding.ivLeft.setOnClickListener {
            finishAffinity(requireActivity())
        }
        viewModelCurrency.itemsCurrency().observe(viewLifecycleOwner, {
                response ->
            when(response){
                is Resource.Success ->{
                    hideProgressBar()
                    response.data?.let {
                            newsResponse ->
                        currencyAdapter.submitList(newsResponse)
                    }
                }
                is Resource.Error ->{
                    hideProgressBar()
                    response.data?.let {
                            message->
                       toast("Error${message}")
                    }

                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun initAdapter() {
        currencyAdapter = CurrencyAdapter()
        binding.rvCurrency.adapter = currencyAdapter
    }

    private fun time() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        binding.tvTimeNow.text = dateFormat.format(calendar.time)
    }
}