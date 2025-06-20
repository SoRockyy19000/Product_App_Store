package com.example.productapp.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.productapp.databinding.ViewHolderProductListBinding
import com.example.productapp.model.ProductModel
import com.squareup.picasso.Picasso

class ProductListViewHolder(private val binding: ViewHolderProductListBinding): RecyclerView.ViewHolder(binding.root) {


    @SuppressLint("SetTextI18n")
    fun bind(product: ProductModel){
        binding.textName.text = product.name
        binding.textPrice.text = "$${product.price}"
        Picasso.get().load(product.imageUrl).into(binding.imageProduct)
    }
    fun setOnNameClickListener(listener: (Int) -> Unit) {
        binding.textName.setOnClickListener {
            listener(adapterPosition)
        }
    }

    fun setOnImageClickListener(listener: (Int) -> Unit) {
        binding.imageProduct.setOnClickListener {
            listener(adapterPosition)
        }
    }

    fun setOnBtnClickListener(listener: (Int) -> Unit){
        binding.btnAddCard.setOnClickListener{
            listener(adapterPosition)
        }
    }

}