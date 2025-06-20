package com.example.productapp.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.productapp.databinding.ViewHolderCartListBinding
import com.example.productapp.model.CartItem
import com.squareup.picasso.Picasso

class CartListViewHolder(private val binding: ViewHolderCartListBinding): RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(item: CartItem){
        binding.itemName.text = item.name
        binding.itemPrice.text = "$${item.price}"
        binding.qty.text = item.quantity.toString()
        Picasso.get().load(item.imageUrl).into(binding.imageProduct)
    }
    fun setOnNameClickListener(listener: (Int) -> Unit) {
        binding.itemName.setOnClickListener {
            listener(adapterPosition)
        }
    }

    fun setOnImageClickListener(listener: (Int) -> Unit) {
        binding.imageProduct.setOnClickListener {
            listener(adapterPosition)
        }
    }
    fun setDeleteClickListener(listener: (Int) -> Unit) {
        binding.btnDelete.setOnClickListener {
            listener(adapterPosition)
        }
    }
    fun setIncreaseClickListener(listener: (Int) -> Unit) {
        binding.btnIncrease.setOnClickListener {
            listener(adapterPosition)
        }
    }
    fun setDecreaseClickListener(listener: (Int) -> Unit) {
        binding.btnDecrease.setOnClickListener {
            listener(adapterPosition)
        }
    }

}