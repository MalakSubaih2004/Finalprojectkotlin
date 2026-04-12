package com.example.finalprojectkotlin.dataClass

data class Product(
    val id: Int,
    val name: String,
    val brand: String,
    val category: String,
    val originalPrice: String,
    val discountPrice: String,
    val discountPercentage: String,
    val soldCount: String,
    val imageRes: Int,
    val description: String
)