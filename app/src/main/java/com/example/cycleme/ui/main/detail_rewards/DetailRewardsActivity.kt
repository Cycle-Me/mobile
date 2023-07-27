package com.example.cycleme.ui.main.detail_rewards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cycleme.databinding.ActivityDetailRewardsBinding

class DetailRewardsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailRewardsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRewardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        setupListener()
    }

    private fun setupListener() {
        binding.ibBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getData() {
        val bundle = intent.extras
        if (bundle != null) {
            val rewardDetail = intent.getStringExtra("rewardDetail")
            val rewardTitle = intent.getStringExtra("rewardTitle")
            val rewardPoint = intent.getStringExtra("rewardPoint")
            val rewardBanner = intent.getIntExtra("rewardBanner", 0)

            binding.tvTitle.text = rewardTitle
            binding.tvPointsNumber.text = rewardPoint
            binding.tvDetailsDescription.text = rewardDetail
            binding.ivBanner.setImageResource(rewardBanner)
        }

    }
}