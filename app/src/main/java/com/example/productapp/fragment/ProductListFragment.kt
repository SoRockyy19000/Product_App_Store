package com.example.productapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productapp.ProductDetailActivity
import com.example.productapp.adapter.ProductListAdapter
import com.example.productapp.databinding.FragmentProductListBinding
import com.example.productapp.model.CartItem
import com.example.productapp.model.ProductModel
import com.example.productapp.services.ApiService
import com.example.productapp.utils.loadCartItems
import com.example.productapp.utils.saveCartItems
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding
    private var adapter: ProductListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // show product list fragment
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // load product data list
        loadProductList()
        handleInitSearchView()
    }

    // create fun to load product list
    private fun  loadProductList(){

        // fetch data from api link base
        val retrofit = Retrofit.Builder()
            .baseUrl("https://smlp-pub.s3.ap-southeast-1.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // connect link base to path while using interface
        val apiService = retrofit.create(ApiService::class.java)

        // fetch data from api while using life cycle
        lifecycleScope.launch {
            // use try catch to handle possible errors
            try {
                val productList = apiService.loadProductList()
                Log.d("RUPPITE","load product list success")

                // call display fun
                displayProductList(productList)
            }catch (ex: Exception){
                Log.e("RUPPITE","load product list error: ${ex.message}")
                Toast.makeText(requireContext(), "Failed to load product list", Toast.LENGTH_SHORT).show()

            }
        }
    }

    // create fun to display data
    private fun displayProductList(productList: List<ProductModel>){

        adapter = ProductListAdapter(productList)

        // Handle name click or image click
        val openProductDetail: (Int) -> Unit = { position ->
            try {
                val product = productList[position]
                Log.d("RUPPITE", "Start passing data from Product List to detail")

                val intent = Intent(requireContext(), ProductDetailActivity::class.java).apply {
                    putExtra("Product_id", product.id)
                }
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("RUPPITE", "Error launching ProductDetailActivity: ${e.message}", e)
                Toast.makeText(requireContext(), "Failed to open product detail", Toast.LENGTH_SHORT).show()
            }
        }
        // Assign the same behavior to both views (or customize differently)
        adapter?.onNameClickListener = openProductDetail
        adapter?.onImageClickListener = openProductDetail

        // add to cart
        adapter?.onBtnClickListener = { position ->
            val product = productList[position]
            val productId = product.id
            val productName = product.name
            val productImageUrl = product.imageUrl
            val productPrice = product.price

            val cartItems = loadCartItems(requireContext()).toMutableList()
            val existingItem = cartItems.find { it.id == productId }
            if (existingItem != null) {
                // Update quantity if product already in cart
                val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
                cartItems[cartItems.indexOf(existingItem)] = updatedItem
                Toast.makeText(requireContext(), "Added to cart!", Toast.LENGTH_SHORT).show()
            } else {
                // Add new item to cart
                val newItem = CartItem(
                    id = productId,
                    name = productName,
                    imageUrl = productImageUrl,
                    price = productPrice.toDouble(),
                    quantity = 1
                )
                cartItems.add(newItem)
                Toast.makeText(requireContext(), "Added to cart!", Toast.LENGTH_SHORT).show()
            }
            saveCartItems(requireContext(), cartItems)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun handleInitSearchView(){
        binding.edtSearch.doAfterTextChanged { edt ->
            val productNotFound = adapter?.filterByName(edt.toString()) == true

            if (productNotFound) {
                binding.emptyStateLayout.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.emptyStateLayout.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }
    }
}