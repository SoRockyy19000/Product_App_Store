package com.example.productapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.productapp.databinding.ViewHolderProductShowBinding
import com.example.productapp.model.ProductModel
import com.example.productapp.viewholder.ProductShowViewHolder

class ProductShowAdapter(private val dataSet: List<ProductModel>): Adapter<ProductShowViewHolder>() {

    var onItemClickListener: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductShowViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderProductShowBinding.inflate(layoutInflater, parent, false)
        return ProductShowViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ProductShowViewHolder, position: Int) {
        holder.bind(dataSet[position])

        holder.itemView.setOnClickListener{
            onItemClickListener?.invoke(position)
        }
    }
}