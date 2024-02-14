package io.github.shvmsaini.sneakership.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.shvmsaini.sneakership.models.Cart
import io.github.shvmsaini.sneakership.repository.SneakersRepository

class CartViewModel(private val repo: SneakersRepository) : ViewModel() {
    private val _cart = MutableLiveData<ArrayList<Cart>>()
    val cart: LiveData<ArrayList<Cart>>
        get() = _cart

    fun getCart() {
        _cart.value = repo.getCart()
    }

    fun removeFromCart(item: Cart) {
        repo.removeFromCart(item)
    }
}