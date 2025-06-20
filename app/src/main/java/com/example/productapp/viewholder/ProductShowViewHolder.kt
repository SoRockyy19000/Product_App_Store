package com.example.productapp.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.productapp.databinding.ViewHolderProductShowBinding
import com.example.productapp.model.ProductModel
import com.squareup.picasso.Picasso

class ProductShowViewHolder(private val binding: ViewHolderProductShowBinding): RecyclerView.ViewHolder(binding.root) {


    @SuppressLint("SetTextI18n")
    fun bind(product: ProductModel){
        binding.textName.text = product.name
        binding.textPrice.text = "$${product.price}"
        Picasso.get().load(product.imageUrl).into(binding.imageProduct)
    }
}