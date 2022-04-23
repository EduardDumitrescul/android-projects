package com.example.workoutcompanion2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_view)
        var navController = findNavController(R.id.fragment_container)
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _, ->
            when (destination.id) {
                R.id.exercise_list_fragment-> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.muscle_list_fragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
                else -> {
                    Log.d(TAG, destination.displayName + destination.id.toString())
                    bottomNavigationView.visibility = View.GONE
                }
            }
        }
    }
}