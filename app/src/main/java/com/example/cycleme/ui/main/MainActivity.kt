package com.example.cycleme.ui.main

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cycleme.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.cycleme.R.layout.activity_main)

        // Set the status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(resources.getColor(R.color.header_pink))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val window: Window = window
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
        supportActionBar?.hide()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navController = findNavController(R.id.nav_host_fragment)

        bottomNavigationView.setupWithNavController(navController)

    }
}