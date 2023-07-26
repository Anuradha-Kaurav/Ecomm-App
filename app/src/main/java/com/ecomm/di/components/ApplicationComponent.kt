package com.ecomm.di.components

import android.content.Context
import com.ecomm.di.modules.DatabaseModule
import com.ecomm.di.modules.NetworkModule
import com.ecomm.di.modules.SubcomponentModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules = [SubcomponentModule::class, NetworkModule::class, DatabaseModule::class])
interface ApplicationComponent {

    fun homeComponent(): HomeComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}