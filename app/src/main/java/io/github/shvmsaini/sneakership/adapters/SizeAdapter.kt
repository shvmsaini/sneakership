package io.github.shvmsaini.sneakership.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.github.shvmsaini.sneakership.R
import io.github.shvmsaini.sneakership.databinding.ItemSizeBinding

class SizeAdapter(
    private val context: Context,
    private val list: ArrayList<Int>,
    private val size: Int
) : RecyclerView.Adapter<SizeAdapter.SneakerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SneakerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context);
        val binding = ItemSizeBinding.inflate(layoutInflater, parent, false)
        return SneakerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SneakerViewHolder, position: Int) {
        val item = list[position]
        holder.size.text = item.toString()
        if (size == item) {
            holder.size.isPressed = true
            holder.size.setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            holder.size.isPressed = false
            holder.size.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        }
    }

    class SneakerViewHolder(binding: ItemSizeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val size = binding.tvSize
    }
}