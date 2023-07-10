package com.ecomm.interfaces.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ecomm.models.Product
import com.ecomm.retrofit.Api
import javax.inject.Inject

class ProductRepository @Inject constructor(private val api: Api) {

    private val _products = MutableLiveData<List<Product>>()
    private val _progressbarStatus = MutableLiveData<Boolean>()

    val products: LiveData<List<Product>>
        get() = _products

    val progressbarStatus : LiveData<Boolean>
        get() = _progressbarStatus

    suspend fun getProducts() {
        _progressbarStatus.postValue(true)
        val result = api.getProducts()
        _progressbarStatus.postValue(false)
        if(result.isSuccessful && result.body() != null){
            _products.postValue(result.body())
        } else{
            Log.d("Product list error", result.message())
        }
    }
}