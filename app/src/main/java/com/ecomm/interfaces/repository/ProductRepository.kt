package com.ecomm.interfaces.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ecomm.db.ProductsDB
import com.ecomm.models.Product
import com.ecomm.retrofit.Api
import javax.inject.Inject

class ProductRepository @Inject constructor(private val api: Api, private val productsDB: ProductsDB) {

    private val _products = MutableLiveData<List<Product>>()
    private val _progressbarStatus = MutableLiveData<Boolean>()
    private val _favProductsList = MutableLiveData<List<Product>>()
    private val _hasFavProduct = MutableLiveData<Boolean>()
    private val _hasProducts = MutableLiveData<Int>()
    private val _isDataExists = MutableLiveData<Boolean>()

    val products: LiveData<List<Product>>
        get() = _products

    val progressbarStatus : LiveData<Boolean>
        get() = _progressbarStatus

    val favProductsList: LiveData<List<Product>>
        get() = _favProductsList

    val hasFavProduct: LiveData<Boolean>
        get() = _hasFavProduct

    val hasProducts: LiveData<Int>
        get() = _hasProducts

    val isDataExists: LiveData<Boolean>
        get() = _isDataExists

    suspend fun getProducts() {
        _progressbarStatus.postValue(true)
        val result = api.getProducts()
//        _progressbarStatus.postValue(false)
        if(result.isSuccessful && result.body() != null){
            _products.postValue(result.body())
        } else{
            Log.d("Product list error", result.message())
        }
    }

    suspend fun addFavProduct(product: Product) {
        productsDB.getFavProductDao().addFavProduct(product)
    }

    suspend fun getFavProducts() {
        _favProductsList.postValue(productsDB.getFavProductDao().getAllFavProducts())
        _progressbarStatus.postValue(false)
    }

    suspend fun hasFavProduct(product: Product) {
        _hasFavProduct.postValue(productsDB.getFavProductDao().hasFavProduct(product.id.toInt()))
    }

    suspend fun deleteFavProduct(product: Product) {
        productsDB.getFavProductDao().deleteFavProduct(product)
    }

    suspend fun hasProducts() {
        _hasProducts.postValue(productsDB.getFavProductDao().hasProducts())
    }

    suspend fun isDataExists() {
        _isDataExists.postValue(productsDB.getFavProductDao().isDataExists())
    }
}