package com.example.currencynb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currencynb.data.EtCurrency
import com.example.currencynb.databinding.ItemsAdapterBinding


class Adapter(): RecyclerView.Adapter<Adapter.CurrencyViewHolder>() {

    inner class CurrencyViewHolder(var binding: ItemsAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<EtCurrency>() {
        override fun areItemsTheSame(oldItem: EtCurrency, newItem: EtCurrency): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EtCurrency, newItem: EtCurrency): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<EtCurrency>) = differ.submitList(list)


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
