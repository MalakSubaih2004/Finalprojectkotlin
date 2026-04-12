package com.example.finalprojectkotlin.viewModel

import androidx.lifecycle.ViewModel
import com.example.finalprojectkotlin.dataClass.CartItem
import com.example.finalprojectkotlin.dataClass.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.plus

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    private val _myOrders = MutableStateFlow<List<CartItem>>(emptyList())
    val myOrders = _myOrders.asStateFlow()

    fun addToCart(product: Product, qty: Int) {
        _cartItems.update { currentList ->
            val existingItem = currentList.find { it.product.id == product.id }
            if (existingItem != null) {
                currentList.map {
                    if (it.product.id == product.id) it.copy(quantity = it.quantity + qty)
                    else it
                }
            } else {
                currentList + CartItem(product, qty)
            }
        }
    }

    fun incrementItem(cartItem: CartItem) {
        _cartItems.update { currentList ->
            currentList.map {
                if (it.product.id == cartItem.product.id) it.copy(quantity = it.quantity + 1)
                else it
            }
        }
    }

    fun decrementItem(cartItem: CartItem) {
        _cartItems.update { currentList ->
            if (cartItem.quantity > 1) {
                currentList.map {
                    if (it.product.id == cartItem.product.id) it.copy(quantity = it.quantity - 1)
                    else it
                }
            } else {
                currentList.filter { it.product.id != cartItem.product.id }
            }
        }
    }

    fun calculateTotal(): Double {
        return _cartItems.value.sumOf {
            it.product.discountPrice.replace("$", "").toDouble() * it.quantity
        }
    }

    fun checkout() {
        if (_cartItems.value.isNotEmpty()) {
            _myOrders.update { it + _cartItems.value }
            _cartItems.value = emptyList()
        }
    }
}