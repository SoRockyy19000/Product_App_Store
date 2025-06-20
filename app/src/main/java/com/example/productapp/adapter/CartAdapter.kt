package com.example.productapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.productapp.databinding.ViewHolderCartListBinding
import com.example.productapp.model.CartItem
import com.example.productapp.viewholder.CartListViewHolder

class CartAdapter(private val items: MutableList<CartItem>) : Adapter<CartListViewHolder>() {

    var onNameClickListener: ((position: Int) -> Unit)? = null
    var onImageClickListener: ((position: Int) -> Unit)? = null
    var onDeleteClickListener: ((position: Int) -> Unit)? = null
    var onIncreaseQtyClickListener: ((posisiton: Int) -> Unit)? = null
    var onDecreaseQtyClickListener: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderCartListBinding.inflate(layoutInflater, parent, false)
        return CartListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        holder.bind(items[position])

        holder.setOnNameClickListener {
            onNameClickListener?.invoke(it)
        }
        holder.setOnImageClickListener {
            onImageClickListener?.invoke(it)
        }
        holder.setDeleteClickListener {
            onDeleteClickListener?.invoke(it)
        }
        holder.setIncreaseClickListener {
            onIncreaseQtyClickListener?.invoke(it)
        }
        holder.setDecreaseClickListener {
            onDecreaseQtyClickListener?.invoke(it)
        }

    }

}