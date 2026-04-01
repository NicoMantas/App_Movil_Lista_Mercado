package com.upb.shoplist

data class ShoppingList(
    val id: String,
    val name: String,
    val products: List<Product>,
    val createdAt: Long = System.currentTimeMillis()
)