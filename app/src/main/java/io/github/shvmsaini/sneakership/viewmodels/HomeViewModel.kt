package io.github.shvmsaini.sneakership.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.shvmsaini.sneakership.models.Cart
import io.github.shvmsaini.sneakership.models.Sneaker
import io.github.shvmsaini.sneakership.repository.SneakersRepository

class HomeViewModel(
    private val repo: SneakersRepository,
) : ViewModel() {
    private val _sneakers = MutableLiveData<ArrayList<Sneaker>>()

    val sneakers: LiveData<ArrayList<Sneaker>>
        get() = _sneakers

    private val _cart = MutableLiveData<ArrayList<Cart>>()

    val cart: LiveData<ArrayList<Cart>>
        get() = _cart

    fun getSneakers() {
        _sneakers.value = repo.getSneakers()
    }

    fun addToCart(item: Cart) {
        repo.addToCart(item)
    }

    fun getCart() {
        _cart.value = repo.getCart()
    }

    fun removeFromCart(item: Cart) {
        repo.removeFromCart(item)
    }
}