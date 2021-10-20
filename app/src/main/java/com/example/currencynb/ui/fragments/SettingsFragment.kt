package com.example.currencynb.ui.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.example.currencynb.R
import com.example.currencynb.adapter.CurrencyAdapter
import com.example.currencynb.adapter.SettingsAdapter
import com.example.currencynb.base.BaseFragment
import com.example.currencynb.databinding.FragmentSettingsBinding
import com.example.currencynb.model.CurrencyResponseItem
import com.example.currencynb.other.Resource
import com.example.currencynb.ui.MainActivity
import com.example.currencynb.ui.ViewModelCurrency
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.concurrent.Task
import java.util.*

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    lateinit var settingsAdapter: SettingsAdapter
    private val viewModelCurrency: ViewModelCurrency by viewModels()
    var touchHelper: ItemTouchHelper? = null


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSettingsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        binding.ivLeft.setOnClickListener {
            findNavController().navigateUp()
        }
        viewModelCurrency.itemsCurrency().observe(viewLifecycleOwner,
            { response ->
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

        touchHelper =
            ItemTouchHelper(object :
                ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
                override fun onMove(
                    p0: RecyclerView,
                    p1: RecyclerView.ViewHolder,
                    p2: RecyclerView.ViewHolder
                ): Boolean {
                    val adapter = p0.adapter as SettingsAdapter
                    val sourcePosition = p1.adapterPosition
                    val targetPosition = p2.adapterPosition

                    adapter.notifyItemMoved(sourcePosition, targetPosition)
                    return true
                }
                override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {

                }

                override fun isLongPressDragEnabled(): Boolean {
                    return false
                }

            })

        touchHelper?.attachToRecyclerView(binding.rvCurrency)
        settingsAdapter.setOnItemClickListner {
            touchHelper!!.startDrag(it)
        }

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