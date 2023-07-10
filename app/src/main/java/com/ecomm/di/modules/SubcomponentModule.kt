package com.ecomm.di.modules

import com.ecomm.di.components.HomeComponent
import dagger.Module

@Module (subcomponents = [HomeComponent::class])
class SubcomponentModule {
}