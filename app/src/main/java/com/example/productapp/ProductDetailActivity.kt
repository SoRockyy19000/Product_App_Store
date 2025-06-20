package com.example.productapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.productapp.databinding.ActivityProductDetialBinding
import com.example.productapp.model.CartItem
import com.example.productapp.model.ProductModel
import com.example.productapp.services.ApiService
import com.example.productapp.utils.loadCartItems
import com.example.productapp.utils.saveCartItems
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityProductDetialBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // get product id from passing
        val productId = intent.getIntExtra("Product_id", -1)
        loadProductDataFromApi(productId)
        Log.d("RUPPITE","Passing data to detail success")
        // back button
        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun loadProductDataFromApi(productId: Int){
        // fetch data from api to compare with passed id
        val retrofit = Retrofit.Builder()
            .baseUrl("https://smlp-pub.s3.ap-southeast-1.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        // connect link base to path while using interface
        val apiService = retrofit.create(ApiService::class.java)
        lifecycleScope.launch {
            try {
                // load data of item
                val productList = apiService.loadProductList()
                val product = productList.find { it.id == productId } // find product id with passed id
                // check product
                if (product != null) {
                    // call display fun to show product
                    displayProductDetail(product)
                    // handle add to cart
                    binding.buttonCart.setOnClickListener{
                        addToCart(product)
                    }
                }
                Log.d("RUPPITE","load product success in product detail ")
            }catch (ex: Exception){
                Log.e("RUPPITE","error load product : ${ex.message} ")
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayProductDetail(productDetail: ProductModel ){
        // show data detail in layout page
        Picasso.get().load(productDetail.imageUrl).into(binding.imageProduct)
        binding.textProductName.text = productDetail.name
        binding.textProductPrice.text =  "$${productDetail.price}"
        binding.textSpecOS.text = "OS: ${productDetail.specs.os}"
        binding.textSpecCPU.text = "CPU: ${productDetail.specs.cpu}"
        binding.textSpecMemory.text = "Memory: ${productDetail.specs.memory}"
        binding.textSpecScreenSize.text = "Screen Size: ${productDetail.specs.screenSize}"
    }

    private fun addToCart(productDetail: ProductModel){
        // load cart item from json file
        val cartItems = loadCartItems(this).toMutableList()
        // check item if it already has then just increase quantity
        val existingItem = cartItems.find { it.id == productDetail.id }
        if (existingItem != null) {
            // If exists, update quantity
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            cartItems[cartItems.indexOf(existingItem)] = updatedItem
            Toast.makeText(this, "Added to cart!", Toast.LENGTH_SHORT).show()
        }else{
            // if no then add new item
            val newItem = CartItem(
                id = productDetail.id,
                name = productDetail.name,
                imageUrl = productDetail.imageUrl,
                price = productDetail.price.toDouble(),
                quantity = 1
            )
            cartItems.add(newItem)
        }
        Toast.makeText(this, "Added to cart!", Toast.LENGTH_SHORT).show()
        // after update then save to json file
        saveCartItems(this, cartItems)

    }
}