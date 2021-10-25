package com.exercise.travelbank_

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        navController = getNavController()


        AppBarConfiguration(
            setOf(
                R.id.expensesFragment,
                R.id.expenseDetailsFragment
            )
        )






    }

    private fun getNavController():NavController{
        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)
        check(fragment is NavHostFragment){
            ("Activity " + this
                    + " does not have a NavHostFragment")
        }

        return fragment.navController
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}