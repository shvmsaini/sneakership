package io.github.shvmsaini.sneakership.interfaces

import io.github.shvmsaini.sneakership.models.Sneaker

/**
 * Callback interface for product item
 */
fun interface ProductItemClickListener {
    fun onItemClick(sneaker: Sneaker, isCart: Boolean)
}