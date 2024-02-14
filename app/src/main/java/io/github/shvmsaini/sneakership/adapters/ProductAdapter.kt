package io.github.shvmsaini.sneakership.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.shvmsaini.sneakership.R
import io.github.shvmsaini.sneakership.databinding.ItemProductBinding
import io.github.shvmsaini.sneakership.interfaces.ProductItemClickListener
import io.github.shvmsaini.sneakership.models.Sneaker

class ProductAdapter(
    private val context: Context,
    private val list: ArrayList<Sneaker>,
    private val listener: ProductItemClickListener
) :
    RecyclerView.Adapter<ProductAdapter.SneakerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SneakerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context);
        val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
        return SneakerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SneakerViewHolder, position: Int) {
        val item = list[position]
        Glide.with(context)
            .load(item.media?.imageUrl)
            .placeholder(R.drawable.shoes)
            .into(holder.image)
        holder.name.text = item.name
        holder.price.text =
            context.getString(R.string.price_x, (item.retailPrice ?: 0).toString())
        holder.itemView.setOnClickListener {
            listener.onItemClick(item, false)
        }
        holder.add.setImageResource(if (item.addedToCart) R.drawable.baseline_clear_24 else R.drawable.baseline_add_24)
        holder.add.setOnClickListener {
            list[position].addedToCart = !list[position].addedToCart
            notifyItemChanged(position)
            listener.onItemClick(list[position], true)
        }
    }

    class SneakerViewHolder(binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.tvName
        val price = binding.tvPrice
        val image = binding.ivSneaker
        val add = binding.ivAdd
    }
}
