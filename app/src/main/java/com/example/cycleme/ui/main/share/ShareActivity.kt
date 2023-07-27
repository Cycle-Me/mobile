package com.example.cycleme.ui.main.share

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cycleme.R
import com.example.cycleme.databinding.ActivityShareBinding

class ShareActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShareBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
    }

    private fun setupListener() {
        binding.ibBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnShare.setOnClickListener {
            val shareDescription: String = getString(R.string.share_cycleme_description)
            shareCycleMe(shareDescription)
        }
    }

    private fun shareCycleMe(content: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, content)
        startActivity(Intent.createChooser(intent, "Share via"))
    }
}