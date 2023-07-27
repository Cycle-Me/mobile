package com.example.cycleme.ui.main.notification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cycleme.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
    }

    private fun setupListener() {
        binding.ibBack.setOnClickListener {
            onBackPressed()
        }
    }
}