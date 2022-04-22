package com.example.workoutcompanion2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_view)
        var navController = findNavController(R.id.fragment_container)
        bottomNavigationView.setupWithNavController(navController)
    }
}