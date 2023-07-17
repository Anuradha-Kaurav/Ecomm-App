package com.ecomm.di.components

import com.ecomm.di.ActivityScope
import com.ecomm.view.activities.HomeActivity
import com.ecomm.view.fragments.FavoritesFragment
import com.ecomm.view.fragments.ProductsFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface HomeComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeComponent
    }

    fun inject(homeActivity: HomeActivity)
    fun inject(productsFragment: ProductsFragment)
    fun inject(favoritesFragment: FavoritesFragment)
}