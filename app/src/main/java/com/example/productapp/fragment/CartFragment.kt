package com.example.productapp.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productapp.ProductDetailActivity
import com.example.productapp.adapter.CartAdapter
import com.example.productapp.databinding.FragmentCartBinding
import com.example.productapp.model.CartItem
import com.example.productapp.utils.loadCartItems
import com.example.productapp.utils.saveCartItems


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cartItems = loadCartItems(requireContext())

        displayCartItems(cartItems)

    }

    @SuppressLint("SetTextI18n")
    private fun displayCartItems(cartItems: MutableList<CartItem>){
        val adapter = CartAdapter(cartItems)
        if (cartItems.isEmpty()) {
            // Show empty layout if cart don't have item
            binding.emptyStateLayout.visibility = View.VISIBLE
            binding.cartRecyclerView.visibility = View.GONE
        } else {
            binding.emptyStateLayout.visibility = View.GONE
            binding.cartRecyclerView.visibility = View.VISIBLE
            // open product detail
            adapter.onNameClickListener = { position -> openProductDetail(position, cartItems) }
            adapter.onImageClickListener = { position -> openProductDetail(position, cartItems) }
            adapter.onDeleteClickListener = { position ->
                val productId = cartItems[position].id
                removeItemFromCart(productId)
            }
            // use adapter to handle Increase product in cart list
            adapter.onIncreaseQtyClickListener = { position ->
                increaseQuantity(position, cartItems)
            }
            // use adapter to handle Decrease product in cart list
            adapter.onDecreaseQtyClickListener = { position ->
                decreaseQuantity(position, cartItems)
            }
        }
        // show cart list in fragment_cart while using recycler view
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter
        // show total prices
        val totalPrice = cartItems.sumOf { it.price * it.quantity }
        binding.textTotalPrice.text = "$%.2f".format(totalPrice)
        binding.buttonCheckout.setOnClickListener{
            Toast.makeText(requireContext(), "Checkout Success", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openProductDetail(position: Int, cartItems: List<CartItem>) {
        try {
            // pass id to product detail
            val product = cartItems[position]
            Log.d("RUPPITE", "Start passing data from Cart to ProductDetail")
            val intent = Intent(requireContext(), ProductDetailActivity::class.java).apply {
                putExtra("Product_id", product.id)
            }
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("RUPPITE", "Error launching ProductDetailActivity: ${e.message}", e)
            Toast.makeText(requireContext(), "Failed to open product detail", Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeItemFromCart(productId: Int) {
        // handle remove item from cart list
        val updatedCart = loadCartItems(requireContext()).filter { it.id != productId } // update product list in json file
        saveCartItems(requireContext(), updatedCart)
        // after save then update item
        displayCartItems(updatedCart.toMutableList())
    }

    private fun increaseQuantity(position: Int, cartItems: MutableList<CartItem>) {
        // handle increase quantity of item
        val item = cartItems[position]
        val updatedItem = item.copy(quantity = item.quantity + 1)
        cartItems[position] = updatedItem
        saveCartItems(requireContext(), cartItems)
        // after save then update item
        displayCartItems(cartItems)
    }

    private fun decreaseQuantity(position: Int, cartItems: MutableList<CartItem>) {
        val item = cartItems[position]
        if (item.quantity > 1) { // check quantity more than 1
            val updatedItem = item.copy(quantity = item.quantity - 1)
            cartItems[position] = updatedItem
            saveCartItems(requireContext(), cartItems)
            // after save then update item
            displayCartItems(cartItems)
        } else {
            Toast.makeText(requireContext(), "Minimum quantity is 1", Toast.LENGTH_SHORT).show()
        }
    }
}