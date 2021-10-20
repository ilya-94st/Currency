package com.example.currencynb.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.currencynb.databinding.ItemsSetingsAdapterBinding
import com.example.currencynb.model.CurrencyResponseItem

class SettingsAdapter: RecyclerView.Adapter<SettingsAdapter.CurrencyViewHolder>() {
    inner class CurrencyViewHolder(var binding: ItemsSetingsAdapterBinding) : RecyclerView.ViewHolder(binding.root)



    private val diffCallback = object : DiffUtil.ItemCallback<CurrencyResponseItem>() {
        override fun areItemsTheSame(oldItem: CurrencyResponseItem, newItem: CurrencyResponseItem): Boolean {
            return oldItem.Cur_ID == newItem.Cur_ID
        }

        override fun areContentsTheSame(oldItem: CurrencyResponseItem, newItem: CurrencyResponseItem): Boolean {
            return oldItem.Cur_ID == newItem.Cur_ID
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<CurrencyResponseItem>) = differ.submitList(list)

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
        holder.binding.tvNameCurrency.text = currency.Cur_Name_Eng
        holder.binding.tvCurrency.text = currency.Cur_Name
        holder.binding.ivTurn.setOnTouchListener { _, event ->
            if(event.actionMasked== MotionEvent.ACTION_DOWN){
                onItemClickListner.let {
                    it(holder)
                }
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
    private var onItemClickListner: (CurrencyViewHolder)->Unit = {article: CurrencyViewHolder -> Unit }

    fun setOnItemClickListner(listner: (CurrencyViewHolder) ->Unit) {
        onItemClickListner = listner
    }
}
