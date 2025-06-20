package com.example.productapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.productapp.ProductDetailActivity
import com.example.productapp.adapter.ProductShowAdapter
import com.example.productapp.databinding.FragmentHomeBinding
import com.example.productapp.model.ProductModel
import com.example.productapp.services.ApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // load product data list
        loadProductShow()
    }

    private fun  loadProductShow(){

        val retrofit = Retrofit.Builder()
            .baseUrl("https://smlp-pub.s3.ap-southeast-1.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        lifecycleScope.launch {
            try {
                val productList = apiService.loadProductList()
                Log.d("RUPPITE","load product show success")
                displayProductList(productList)
            }catch (ex: Exception){
                Log.e("RUPPITE","load product show error: ${ex.message}")

            }

        }

    }


    private fun displayProductList(productList: List<ProductModel>){

        val adapter = ProductShowAdapter(productList)
        adapter.onItemClickListener = { position ->
            try {
                val product = productList[position]
                Log.d("RUPPITE", "Start passing data from Home Show to detail")

                val intent = Intent(requireContext(), ProductDetailActivity::class.java).apply {
                    putExtra("Product_id", product.id)
                }
                startActivity(intent)

            } catch (e: Exception) {
                Log.e("RUPPITE", "Error launching ProductDetailActivity: ${e.message}", e)
                Toast.makeText(requireContext(), "Failed to open product detail", Toast.LENGTH_SHORT).show()
            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

    }
}