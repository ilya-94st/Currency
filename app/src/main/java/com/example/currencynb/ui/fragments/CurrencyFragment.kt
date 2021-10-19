package com.example.currencynb.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.currencynb.adapter.CurrencyAdapter
import com.example.currencynb.base.BaseFragment
import com.example.currencynb.databinding.FragmentCurrencyBinding
import com.example.currencynb.other.Resource
import com.example.currencynb.ui.MainActivity
import com.example.currencynb.ui.ViewModelCurrency


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
        viewModelCurrency.itemsCurrency().observe(viewLifecycleOwner, Observer { // вешаем наблюдателя на наши breackingNews данные во ViewModel
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
}