package io.github.shvmsaini.sneakership.adapters

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.github.shvmsaini.sneakership.R
import io.github.shvmsaini.sneakership.databinding.ItemColorBinding

class ColorAdapter(
    private val context: Context,
    private val list: ArrayList<String>,
    private val color: String
) : RecyclerView.Adapter<ColorAdapter.SneakerColorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SneakerColorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context);
        val binding = ItemColorBinding.inflate(layoutInflater, parent, false)
        return SneakerColorViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SneakerColorViewHolder, position: Int) {
        when (val item = list[position]) {
            "Blue" -> {
                setColor(
                    holder.color,
                    ContextCompat.getColor(context, R.color.colorBlue),
                    color == item
                )
            }

            "Red" -> {
                setColor(
                    holder.color,
                    ContextCompat.getColor(context, R.color.colorLightPrimary),
                    color == item
                )
            }

            "Black" -> {
                setColor(
                    holder.color,
                    ContextCompat.getColor(context, R.color.black),
                    color == item
                )
            }
        }

    }

    private fun setColor(colorImage: ImageView, color: Int, isSelected: Boolean) {
        (colorImage.background.mutate() as GradientDrawable).setColor(color)
        if (isSelected) {
            colorImage.setColorFilter(
                ContextCompat.getColor(context, R.color.white),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        } else {
            colorImage.setColorFilter(
                color,
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        }
    }

    class SneakerColorViewHolder(binding: ItemColorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val color = binding.ivColor
    }
}