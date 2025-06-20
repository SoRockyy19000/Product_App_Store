package com.example.productapp.utils

import android.content.Context
import com.example.productapp.model.CartItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// handle save cart item to json file
fun saveCartItems(context: Context, cartItems: List<CartItem>) {
    val sharedPref = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    val gson = Gson()
    val json = gson.toJson(cartItems)
    editor.putString("cart_items", json)
    editor.apply()
}

// handle load cart item from json file
fun loadCartItems(context: Context): MutableList<CartItem> {
    val sharedPref = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    val gson = Gson()
    val json = sharedPref.getString("cart_items", null)
    return if (json != null) {
        val type = object : TypeToken<List<CartItem>>() {}.type
        gson.fromJson(json, type)
    } else {
        mutableListOf()
    }
}
