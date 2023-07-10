package com.ecomm

import android.app.Application
import com.ecomm.di.components.ApplicationComponent
import com.ecomm.di.components.DaggerApplicationComponent

class MyApplication: Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}