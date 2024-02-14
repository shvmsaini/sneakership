package io.github.shvmsaini.sneakership.interfaces

import io.github.shvmsaini.sneakership.models.Cart


/**
 * Callback interface for cart item
 */
fun interface CartItemClickListener {
    fun onItemClick(item: Cart)
}