package com.example.cycleme.ui.main.detail_story

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cycleme.databinding.ActivityDetailStoryBinding
import com.example.cycleme.model.StoriesResponse

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getData()
    }

    private fun getData() {
        val item = intent.getParcelableExtra<StoriesResponse>("detailStory")

        if (item != null) {
            getDetail(item)
        }
    }

    private fun getDetail(data: StoriesResponse) {
        Glide.with(this)
            .load(data.attachment)
            .into(binding.ivStory)

        binding.tvUsername.text = data.user?.name
        binding.tvDescription.text = data.description
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}