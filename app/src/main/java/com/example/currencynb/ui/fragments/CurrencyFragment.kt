package com.example.currencynb.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.currencynb.R
import com.example.currencynb.adapter.CurrencyAdapter
import com.example.currencynb.base.BaseFragment
import com.example.currencynb.databinding.FragmentCurrencyBinding
import com.example.currencynb.other.Resource
import com.example.currencynb.main.viewModel.ViewModelCurrency
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class CurrencyFragment : BaseFragment<FragmentCurrencyBinding>() {
    private lateinit var currencyAdapter: CurrencyAdapter
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
        lifecycleScope.launchWhenCreated {
            viewModelCurrency.itemsCurrency().collect {
                    response ->
                when(response){
                    is Resource.Success ->{
                        hideProgressBar()
                        hideErrorMessage()
                        response.data?.let {
                                currencyResponse ->
                            currencyAdapter.submitList(currencyResponse)
                        }
                    }
                    is Resource.Error ->{
                        hideProgressBar()
                        response.message?.let {
                                message->
                            toast("Error${message}")
                            showErrorMessage(message)
                        }

                    }
                    is Resource.Loading ->{
                        showProgressBar()
                    }
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.ivSettings.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.ivSettings.visibility = View.INVISIBLE
    }

    private fun initAdapter() {
        currencyAdapter = CurrencyAdapter()
        binding.rvCurrency.adapter = currencyAdapter
    }

    private fun hideErrorMessage() {
        binding.tvError.visibility = View.INVISIBLE
    }

    private fun showErrorMessage(message: String) {
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = message
        binding.ivSettings.visibility = View.INVISIBLE

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