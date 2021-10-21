package com.example.currencynb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currencynb.databinding.ItemsAdapterBinding
import com.example.currencynb.model.CurrencyRatesItem

class CurrencyAdapter: RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    inner class CurrencyViewHolder(var binding: ItemsAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<CurrencyRatesItem>() {
        override fun areItemsTheSame(oldItem: CurrencyRatesItem, newItem: CurrencyRatesItem): Boolean {
            return oldItem.Cur_ID == newItem.Cur_ID
        }

        override fun areContentsTheSame(oldItem: CurrencyRatesItem, newItem: CurrencyRatesItem): Boolean {
            return oldItem.Cur_ID == newItem.Cur_ID
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<CurrencyRatesItem>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding = ItemsAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = differ.currentList[position]
        holder.binding.tvNameCurrency.text = currency.Cur_Name
        holder.binding.tvCurrency.text = currency.Cur_Abbreviation
        holder.binding.tvChanged.text = currency.Cur_OfficialRate.toString()
        holder.binding.tvSaled.text = currency.Cur_OfficialRate.toString()

    }
}
