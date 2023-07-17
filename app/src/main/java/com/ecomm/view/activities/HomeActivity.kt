package com.ecomm.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.ecomm.MyApplication
import com.ecomm.R
import com.ecomm.databinding.ActivityHomeBinding
import com.ecomm.di.components.HomeComponent
import com.ecomm.viewmodel.HomeViewModelFactory
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    lateinit var homeComponent: HomeComponent

    @Inject
    lateinit var mainViewModelFactory: HomeViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {

        homeComponent = (applicationContext as MyApplication).applicationComponent.homeComponent().create()
        homeComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_layout) as NavHostFragment?

        setupWithNavController(binding.bottomNav, navHostFragment!!.navController)
    }
}