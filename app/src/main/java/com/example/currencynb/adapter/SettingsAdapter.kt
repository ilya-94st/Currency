package com.example.currencynb.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currencynb.databinding.ItemsSetingsAdapterBinding
import com.example.currencynb.model.CurrencyRatesItem

class SettingsAdapter: RecyclerView.Adapter<SettingsAdapter.CurrencyViewHolder>() {
    inner class CurrencyViewHolder(var binding: ItemsSetingsAdapterBinding) : RecyclerView.ViewHolder(binding.root)



    private val diffCallback = object : DiffUtil.ItemCallback<CurrencyRatesItem>() {
        override fun areItemsTheSame(oldItem: CurrencyRatesItem, newItem: CurrencyRatesItem): Boolean {
            return oldItem.Cur_ID == newItem.Cur_ID
        }

        override fun areContentsTheSame(oldItem: CurrencyRatesItem, newItem: CurrencyRatesItem): Boolean {
            return oldItem.Cur_ID == newItem.Cur_ID
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var songs: List<CurrencyRatesItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding = ItemsSetingsAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = differ.currentList[position]
        holder.binding.tvNameCurrency.text = currency.Cur_Name
        holder.binding.tvCurrency.text = currency.Cur_Abbreviation
        holder.binding.ivTurn.setOnTouchListener { _, event ->
            if(event.actionMasked== MotionEvent.ACTION_DOWN){
                onItemClickListener(holder)
            }
            false
        }
        holder.binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
            holder.binding.tvCurrency.visibility = View.INVISIBLE
            holder.binding.tvNameCurrency.visibility = View.INVISIBLE
            } else {
                holder.binding.tvCurrency.visibility = View.VISIBLE
                holder.binding.tvNameCurrency.visibility = View.VISIBLE
            }
        }
    }
    private var onItemClickListener: (CurrencyViewHolder)->Unit = { article: CurrencyViewHolder -> Unit }

    fun setOnItemClickListener(listener: (CurrencyViewHolder) ->Unit) {
        onItemClickListener = listener
    }
}
