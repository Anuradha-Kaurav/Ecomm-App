package com.ecomm.di.modules

import android.content.Context
import androidx.room.Room
import com.ecomm.db.ProductsDB
import com.ecomm.utils.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesProductsDB(context: Context): ProductsDB {
        return Room.databaseBuilder(context, ProductsDB::class.java, Constants.DB_NAME).build()
    }
}