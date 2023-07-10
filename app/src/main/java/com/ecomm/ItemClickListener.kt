package com.ecomm

interface ItemClickListener<T> {

    fun onItemClick(item: T)
    fun onFavClick(item: T)
}