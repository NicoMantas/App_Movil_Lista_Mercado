package com.upb.shoplist

import java.io.Serializable

data class Product(
    val id: String = "",
    val name: String = "",
    val category: String = "",
    val quantity: Int = 0,
    val price: Double = 0.0,
    var isPurchased: Boolean = false
) : Serializable
