package com.example.cycleme.ui.main.detail_recycle

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cycleme.adapters.RecommendationAdapter
import com.example.cycleme.databinding.ActivityDetailRecycleBinding
import com.example.cycleme.model.RecommendationRequest
import com.example.cycleme.model.RecommendationResponse
import com.example.cycleme.utils.Result

class DetailRecycleActivity : AppCompatActivity() {
    private lateinit var adapter: RecommendationAdapter
    private lateinit var binding: ActivityDetailRecycleBinding
    private val viewModel by viewModels<DetailRecycleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRecycleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category = intent.getStringExtra("label")
        if (category != null) {
            binding.tvCategory.text = category
        }
        getRecommendation(category!!)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = RecommendationAdapter()

        binding.rvRecommendation.layoutManager = LinearLayoutManager(this)
        binding.rvRecommendation.adapter = adapter

    }

    private fun getRecommendation(category: String) {
        viewModel.resultRecommendation.observe(this) { it ->
            when (it) {
                is Result.Success -> {
                    val recommendationList: List<RecommendationResponse?> = it.data
                    recommendationList.let {
                        adapter.setData(it)
                    }
                    showLoading(false)
                }

                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Error -> {
                    showToast(it.error)
                    Log.e("ActivityDetailRecycle ", it.error)
                    showLoading(false)
                }
            }
        }
        viewModel.getRecommendation(RecommendationRequest(category))
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}