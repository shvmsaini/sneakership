package io.github.shvmsaini.sneakership.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import io.github.shvmsaini.sneakership.models.Cart
import io.github.shvmsaini.sneakership.repository.SneakersRepository

class DetailsViewModel(private val repo: SneakersRepository) : ViewModel() {
    fun addToCart(item: Cart) {
        repo.addToCart(item)
    }

    fun removeFromCart(item: Cart) {
        repo.removeFromCart(item)
    }
}