package com.example.cycleme.ui.main.upload_result

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cycleme.adapters.RecommendationAdapter
import com.example.cycleme.databinding.ActivityUploadResultBinding
import com.example.cycleme.model.RecommendationRequest
import com.example.cycleme.model.RecommendationResponse
import com.example.cycleme.utils.Result

class UploadResultActivity : AppCompatActivity() {
    private lateinit var adapter: RecommendationAdapter
    private lateinit var binding: ActivityUploadResultBinding
    private val uploadResultViewModel by viewModels<UploadResultViewModel>()
    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        if (bundle != null) {
            category = bundle.getString("cat").toString()
            binding.apply {
                tvAccuracyResult.text = bundle.getString("acc")
                tvCategoryResult.text = category
            }
        }

        getRecommendation(category)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = RecommendationAdapter()

        binding.rvRecommendation.layoutManager = LinearLayoutManager(this@UploadResultActivity)
        binding.rvRecommendation.adapter = adapter

    }

    private fun getRecommendation(category: String) {
        uploadResultViewModel.getRecommendation(RecommendationRequest(category))

        uploadResultViewModel.resultRecommendation.observe(this@UploadResultActivity) { it ->
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
                    Log.e("UploadResultActivity ", it.error)
                    showLoading(false)
                }
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this@UploadResultActivity, text, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}