package com.ecomm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecomm.models.Product
import com.ecomm.interfaces.repository.ProductRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeViewModel(private val repository: ProductRepository): ViewModel() {

    val productsLiveData : LiveData<List<Product>>
        get() = repository.products

    val progressbarStatus: LiveData<Boolean>
        get() = repository.progressbarStatus

    val favProductsListLiveData: LiveData<List<Product>>
        get() = repository.favProductsList

    val hasProductsLiveData: LiveData<Int>
        get() = repository.hasProducts

    val isExistsLiveData: LiveData<Boolean>
        get() = repository.isDataExists

    /*init {
        viewModelScope.launch {
            repository.getProducts()
        }
    }*/

    fun getAllProducts() {
        viewModelScope.launch {
            repository.getProducts()
        }
    }

    fun getFavProducts() {
        viewModelScope.launch {
            repository.getFavProducts()
        }
    }

    fun addFavProduct(product: Product) {
        viewModelScope.launch {
            repository.addFavProduct(product)
        }
    }

    fun deleteFavProduct(product: Product) = runBlocking {
        val job = launch { repository.deleteFavProduct(product)}
        job.join()
    }

    fun hasProducts() {
        viewModelScope.launch {
            repository.hasProducts()
        }
    }

    fun isDataExists() {
        viewModelScope.launch {
            repository.isDataExists()
        }
    }

}