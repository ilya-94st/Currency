package com.example.currencynb.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.currencynb.R
import com.example.currencynb.adapter.SettingsAdapter
import com.example.currencynb.base.BaseFragment
import com.example.currencynb.databinding.FragmentSettingsBinding
import com.example.currencynb.other.Resource
import com.example.currencynb.main.viewModel.ViewModelCurrency
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    private lateinit var settingsAdapter: SettingsAdapter
    private var isSave: Boolean = false
    private val viewModelCurrency: ViewModelCurrency by viewModels()
    private var touchHelper: ItemTouchHelper? = null

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSettingsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        saveData()
        binding.ivLeft.setOnClickListener {
            findNavController().navigateUp()
        }
        lifecycleScope.launchWhenCreated {
            viewModelCurrency.itemsCurrency().collect {
                    response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { newsResponse ->
                            settingsAdapter.songs = newsResponse
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
            }
        }

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
        settingsAdapter.setOnItemClickListener {
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

    private fun saveData() {
       binding.ivSettings.setOnClickListener {
          // viewModelCurrency.insert(settingsAdapter.songs)
           isSave = true
           snackBar("save data")
           val bundle = Bundle().apply {
               putBoolean("save", isSave)
           }
           findNavController().navigate(
               R.id.action_settingsFragment_to_currencyFragment,
               bundle
           )

       }
    }
}