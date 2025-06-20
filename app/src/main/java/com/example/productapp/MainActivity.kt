package com.example.productapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.productapp.databinding.ActivityMainBinding
import com.example.productapp.fragment.AccountFragment
import com.example.productapp.fragment.CartFragment
import com.example.productapp.fragment.HomeFragment
import com.example.productapp.fragment.ProductListFragment
import com.example.productapp.fragment.CategoryFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val homeFragment = HomeFragment()
    private val productFragment = ProductListFragment()
    private val categoryFragment = CategoryFragment()
    private val cartFragment = CartFragment()
    private val accountFragment = AccountFragment()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setUpFragment()
        handleMenuNavigation()
        showHomeFragment()


    }
// handle add data for first but not reload
//    private  fun setUpFragment(){
//        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, homeFragment).commit()
//        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, productFragment).commit()
//        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, searchFragment).commit()
//        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, cartFragment).commit()
//        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, accountFragment).commit()
//
//
//    }

    // handle menu order
    private fun handleMenuNavigation(){
        binding.BottomMenuNavigation.setOnItemSelectedListener { menuItem ->

            when(menuItem.itemId){
                R.id.menuHome -> showHomeFragment()
                R.id.menuProductList -> showProductFragment()
                R.id.menuCategory -> showCategoryFragment()
                R.id.menuCart -> showCartFragment()
                else -> showAccountFragment()
            }
            true
        }
    }

    // handle set up show hide fragment
//    private fun showFragment(fragmentToShow: Fragment) {
//        supportFragmentManager.beginTransaction().apply {
//            listOf(homeFragment, productFragment, searchFragment,cartFragment, accountFragment).forEach { fragment ->
//                if (fragment == fragmentToShow) {
//                    show(fragment)
//                } else {
//                    hide(fragment)
//                }
//            }
//        }.commit()
//    }

    private fun showHomeFragment(){
//        showFragment(homeFragment)
        supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, homeFragment).commit()
    }

    private fun showProductFragment(){
//        showFragment(productFragment)
        supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, productFragment).commit()
    }

    private fun showCategoryFragment(){
//        showFragment(searchFragment)
        supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, categoryFragment).commit()
    }

    private fun showCartFragment() {
//        showFragment(cartFragment)
        supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, cartFragment).commit()
    }

    private fun showAccountFragment(){
//        showFragment(accountFragment)
        supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, accountFragment).commit()

    }



}