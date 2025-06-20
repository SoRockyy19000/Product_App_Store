package com.example.productapp.model

data class ProductModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Int,
    val specs: Specs
)

data class Specs(
    val os: String,
    val cpu: String,
    val memory: String,
    val screenSize: String
)

data class CartItem(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val quantity: Int
)

