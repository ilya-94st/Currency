package com.example.currencynb.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.currencynb.R
import com.example.currencynb.adapter.CurrencyAdapter
import com.example.currencynb.adapter.SettingsAdapter
import com.example.currencynb.base.BaseFragment
import com.example.currencynb.databinding.FragmentSettingsBinding
import com.example.currencynb.other.Resource
import com.example.currencynb.ui.MainActivity
import com.example.currencynb.ui.ViewModelCurrency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    lateinit var settingsAdapter: SettingsAdapter
    private val viewModelCurrency: ViewModelCurrency by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSettingsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        binding.ivLeft.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_currencyFragment)
        }
        viewModelCurrency.itemsCurrency().observe(viewLifecycleOwner,
            {
                    response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { newsResponse ->
                            settingsAdapter.submitList(newsResponse)
                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.data?.let { message ->
                            toast("Error${message}")
                        }

                    }
                    is Resource.Loading -> {
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
        settingsAdapter = SettingsAdapter()
        binding.rvCurrency.adapter = settingsAdapter
    }
}