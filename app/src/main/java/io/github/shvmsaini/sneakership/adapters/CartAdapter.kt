package io.github.shvmsaini.sneakership.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.shvmsaini.sneakership.R
import io.github.shvmsaini.sneakership.databinding.ItemCartBinding
import io.github.shvmsaini.sneakership.interfaces.CartItemClickListener
import io.github.shvmsaini.sneakership.models.Cart

class CartAdapter(
    private val context: Context,
    private val list: ArrayList<Cart>,
    private val listener: CartItemClickListener,
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(layoutInflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = list[position]

        Glide.with(context)
            .load(item.image)
            .placeholder(R.drawable.shoes)
            .into(holder.image)

        holder.name.text = item.name
        holder.price.text =
            context.getString(R.string.price_x, (item.retailPrice ?: 0).toString())
        holder.clear.setOnClickListener {
            list.removeAt(position)
            notifyItemChanged(position)
            listener.onItemClick(item)
        }
    }
    class CartViewHolder(binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.tvName
        val price = binding.tvPrice
        val image = binding.ivSneaker
        val clear = binding.ivClear
    }
}