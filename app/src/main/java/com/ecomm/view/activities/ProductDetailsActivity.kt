package com.ecomm.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.bumptech.glide.Glide
import com.ecomm.MyApplication
import com.ecomm.R
import com.ecomm.databinding.ActivityProductDetailsBinding
import com.ecomm.db.ProductsDB
import com.ecomm.di.components.HomeComponent
import com.ecomm.models.Product
import com.ecomm.utils.Constants
import com.ecomm.utils.Constants.PRODUCT
import com.ecomm.utils.Constants.parcelable
import com.ecomm.viewmodel.HomeViewModel
import com.ecomm.viewmodel.HomeViewModelFactory
import javax.inject.Inject

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductDetailsBinding
    private lateinit var receivedProduct: Product

    lateinit var homeViewModel: HomeViewModel
    lateinit var homeComponent: HomeComponent

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    @Inject
    lateinit var roomDB: ProductsDB

    override fun onCreate(savedInstanceState: Bundle?) {
        homeComponent = (applicationContext as MyApplication).applicationComponent.homeComponent().create()
        homeComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        roomDB = Room.databaseBuilder(this, ProductsDB::class.java, Constants.DB_NAME).build()
    }

    override fun onResume() {
        super.onResume()
        receivedProduct = intent?.parcelable(PRODUCT)!!
        homeViewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
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
           Constants.selectFav( binding.favBtn, this)
        }

        binding.favBtn.setOnClickListener {
            if(receivedProduct.isInWishlist){
                receivedProduct.isInWishlist = false
                //remove to db
                homeViewModel.deleteFavProduct(receivedProduct)
                Constants.unselectFav( binding.favBtn, this)
            }else{
                receivedProduct.isInWishlist = true
                //add from db
                homeViewModel.addFavProduct(receivedProduct)
                Constants.selectFav( binding.favBtn, this)
            }
        }
    }
}