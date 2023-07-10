package com.ecomm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecomm.models.Product
import com.ecomm.interfaces.repository.ProductRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ProductRepository): ViewModel() {

    val productsLiveData : LiveData<List<Product>>
        get() = repository.products

    val progressbarStatus: LiveData<Boolean>
        get() = repository.progressbarStatus

    init {
        viewModelScope.launch {
            repository.getProducts()
        }
    }

}