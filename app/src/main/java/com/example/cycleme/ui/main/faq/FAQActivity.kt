package com.example.cycleme.ui.main.faq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cycleme.databinding.ActivityFaqactivityBinding

class FAQActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFaqactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
    }

    private fun setupListener() {
        binding.ibBack.setOnClickListener {
            onBackPressed()
        }
    }
}