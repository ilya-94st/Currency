package com.example.currencynb.ui.fragments

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.currencynb.R
import com.example.currencynb.adapter.CurrencyAdapter
import com.example.currencynb.base.BaseFragment
import com.example.currencynb.databinding.FragmentCurrencyBinding
import com.example.currencynb.other.Resource
import com.example.currencynb.ui.MainActivity
import com.example.currencynb.ui.ViewModelCurrency
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.days

@AndroidEntryPoint
class CurrencyFragment : BaseFragment<FragmentCurrencyBinding>() {
    lateinit var currencyAdapter: CurrencyAdapter
    private val viewModelCurrency: ViewModelCurrency by viewModels()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCurrencyBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        val dayPlus = calendar.get(Calendar.DAY_OF_MONTH) + 1
        val dateFormat2 = SimpleDateFormat("${dayPlus}.MM.yy", Locale.getDefault())
        binding.tvTimeTomorrow.text = dateFormat2.format(calendar.time)
    }
}