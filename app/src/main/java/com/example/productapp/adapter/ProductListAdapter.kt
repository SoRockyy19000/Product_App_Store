package com.example.productapp.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.productapp.databinding.ViewHolderProductListBinding
import com.example.productapp.model.ProductModel
import com.example.productapp.viewholder.ProductListViewHolder

class ProductListAdapter(private var dataSet: List<ProductModel>): Adapter<ProductListViewHolder>() {


    private var allProductDataSet: List<ProductModel> = dataSet
    var onNameClickListener: ((position: Int) -> Unit)? = null
    var onImageClickListener: ((position: Int) -> Unit)? = null
    var onBtnClickListener: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderProductListBinding.inflate(layoutInflater, parent, false)
        return ProductListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(dataSet[position])

        holder.setOnNameClickListener {
            onNameClickListener?.invoke(it)
        }

        holder.setOnImageClickListener {
            onImageClickListener?.invoke(it)
        }

        holder.setOnBtnClickListener {
            onBtnClickListener?.invoke(it)
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun filterByName(keyWord: String): Boolean {
        Log.d("RUPPITE","Key word: $keyWord")
        dataSet = allProductDataSet.filter {product -> product.name.contains(keyWord,true) }
        notifyDataSetChanged()
        return dataSet.isEmpty()
    }

}