package io.github.shvmsaini.sneakership.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.shvmsaini.sneakership.repository.SneakersRepository

class DetailsViewModelFactory(private val repo: SneakersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SneakersRepository::class.java).newInstance(repo)
    }
}