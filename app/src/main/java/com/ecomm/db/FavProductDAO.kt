package com.ecomm.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ecomm.models.Product

@Dao
interface FavProductDAO {

    //add products
    @Insert
    suspend fun addFavProduct(product: Product)

    //get all products
    @Query("SELECT * FROM Product")
    suspend fun getAllFavProducts(): List<Product>

    //delete product
    @Delete
    suspend fun deleteFavProduct(product: Product)

    //check if selected product exists
    @Query("SELECT EXISTS(SELECT * FROM Product WHERE id = :id)")
    suspend fun hasFavProduct(id: Int): Boolean

    //check if any product exists
    @Query("SELECT COUNT(*) FROM Product")
    suspend fun hasProducts() : Int

    @Query("SELECT EXISTS(SELECT * FROM Product)")
    suspend fun isDataExists(): Boolean
}