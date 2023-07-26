package com.ecomm.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.ecomm.ItemClickListener
import com.ecomm.R
import com.ecomm.databinding.FragmentFavoritesBinding
import com.ecomm.db.ProductsDB
import com.ecomm.models.Product
import com.ecomm.utils.Constants
import com.ecomm.view.activities.HomeActivity
import com.ecomm.view.activities.ProductDetailsActivity
import com.ecomm.view.adapters.FavoritesAdapter
import com.ecomm.viewmodel.HomeViewModel
import com.ecomm.viewmodel.HomeViewModelFactory
import javax.inject.Inject

class FavoritesFragment : Fragment(), ItemClickListener<Product> {

    private lateinit var binding : FragmentFavoritesBinding
    private val adapter = FavoritesAdapter(this)

    lateinit var homeViewModel: HomeViewModel
    private lateinit var productsList: List<Product>

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    @Inject
    lateinit var roomDB: ProductsDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.productsRv.adapter = adapter

        roomDB = Room.databaseBuilder((activity as HomeActivity), ProductsDB::class.java, Constants.DB_NAME)
            .build()

        setUpViewModel()
    }

    override fun onResume() {
        super.onResume()
        fetchFavViewModelData()
    }

    private fun fetchFavViewModelData() {
        //check if there is any fav prod in db
        homeViewModel.hasProducts()

        homeViewModel.hasProductsLiveData.observe(this, Observer {
            if(it>0){
                binding.noFavText.visibility = View.GONE
                getAllFavProducts()
            }else{
                binding.noFavText.visibility = View.VISIBLE
            }
        })
    }

    private fun getAllFavProducts() {
        homeViewModel.getFavProducts()
        homeViewModel.favProductsListLiveData.observe(this, Observer{ products ->
            productsList = products
            adapter.setProductList(productsList)
        })
    }

    private fun setUpViewModel() {
        (activity as HomeActivity).homeComponent.inject(this)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }

    override fun onItemClick(item: Product) {
        val intent = Intent(context, ProductDetailsActivity::class.java)
        intent.putExtra(Constants.PRODUCT, item)
        startActivity(intent)
    }

    override fun onFavClick(item: Product) {
        if(adapter.itemCount == 1){
            binding.noFavText.visibility = View.VISIBLE
        }else{
            binding.noFavText.visibility = View.GONE
        }

        homeViewModel.deleteFavProduct(item)
        getAllFavProducts()
    }

}