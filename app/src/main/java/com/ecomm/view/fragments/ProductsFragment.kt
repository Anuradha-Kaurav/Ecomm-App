package com.ecomm.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ecomm.ItemClickListener
import com.ecomm.databinding.FragmentProductsBinding
import com.ecomm.models.Product
import com.ecomm.utils.Constants.PRODUCT
import com.ecomm.view.activities.HomeActivity
import com.ecomm.view.activities.ProductDetailsActivity
import com.ecomm.view.adapters.ProductsAdapter
import com.ecomm.viewmodel.HomeViewModel
import com.ecomm.viewmodel.HomeViewModelFactory
import javax.inject.Inject

class ProductsFragment : Fragment(), ItemClickListener<Product> {

    private lateinit var binding: FragmentProductsBinding
    private val adapter = ProductsAdapter(this)

    private lateinit var homeViewModel : HomeViewModel
    private lateinit var productsList: List<Product>

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.productsRv.adapter = adapter
        setUpViewModel()
    }

    override fun onResume() {
        super.onResume()
        fetchViewModel()
        handleProgressbarStatus()
    }

    private fun handleProgressbarStatus() {
        homeViewModel.progressbarStatus.observe(this, Observer {
            if(it) {
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    private fun fetchViewModel() {
        homeViewModel.productsLiveData.observe(this, Observer {
            productsList = it.toMutableList()
            adapter.setProductList(productsList)
        })
    }

    private fun setUpViewModel() {
        (activity as HomeActivity).homeComponent.inject(this)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }

    override fun onItemClick(item: Product) {
        val intent = Intent(context, ProductDetailsActivity::class.java)
        intent.putExtra(PRODUCT, item)
        startActivity(intent)
    }

    override fun onFavClick(item: Product) {
        if(item.isInWishlist){
            //add to db
        }else{
            //remove from db
        }
    }


}