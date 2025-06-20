package com.example.productapp.services

import com.example.productapp.model.ProductModel
import retrofit2.http.GET

interface ApiService {
    @GET("/mad-g9/product-list.json")
    suspend fun loadProductList(): List<ProductModel>
}