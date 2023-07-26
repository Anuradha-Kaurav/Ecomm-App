package com.ecomm.utils

import android.content.Context
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Parcelable
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import com.ecomm.R

object Constants {

    const val BASE_URL = "https://fakestoreapi.com/"
    const val PRODUCT = "product"
    const val DB_NAME = "ProductsDB"

    inline fun <reified  T : Parcelable> Intent.parcelable(key : String): T? = when {
        SDK_INT >=33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

    fun selectFav(favIcon: ImageView, context: Context) {
        favIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_favorite_selected))
    }

    fun unselectFav(favIcon: ImageView, context: Context) {
        favIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_bottom_favorites))
    }
}