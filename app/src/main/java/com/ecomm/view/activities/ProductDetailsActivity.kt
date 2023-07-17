package com.ecomm.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ecomm.R
import com.ecomm.databinding.ActivityProductDetailsBinding
import com.ecomm.models.Product
import com.ecomm.utils.Constants.PRODUCT
import com.ecomm.utils.Constants.parcelable

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductDetailsBinding
    private lateinit var receivedProduct: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        receivedProduct = intent?.parcelable(PRODUCT)!!
        initUi()
    }

    private fun initUi() {
        binding.productName.text = receivedProduct.title.toString()
        binding.productPrice.text = "$ " + receivedProduct.price
        binding.ratings.rating = receivedProduct.rating.rate.toFloat()
        binding.ratingCount.text = "("+ receivedProduct.rating.count.toString()+")"

        Glide.with(this)
            .load(receivedProduct.image)
            .placeholder(R.drawable.ic_image_placeholder)
            .into(binding.productImage)

        binding.backBtn.setOnClickListener { finish() }

        if(receivedProduct.isInWishlist){

        }
    }
}