package com.example.finalprojectkotlin.viewModel

import androidx.lifecycle.ViewModel
import com.example.finalprojectkotlin.dataClass.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoritesViewModel : ViewModel() {
    private val _favoriteItems = MutableStateFlow<List<Product>>(emptyList())

    val favoriteItems: StateFlow<List<Product>> = _favoriteItems.asStateFlow()

    fun toggleFavorite(product: Product) {
        val currentList = _favoriteItems.value.toMutableList()

        val existing = currentList.find { it.id == product.id }

        if (existing != null) {
            currentList.remove(existing)
        } else {
            currentList.add(product)
        }
        _favoriteItems.value = currentList
    }

    fun isFavorite(product: Product): Boolean {
        return _favoriteItems.value.any { it.id == product.id }
    }
}