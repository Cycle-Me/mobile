package com.example.cycleme.ui.main.upload_result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cycleme.adapters.RecommendationAdapter
import com.example.cycleme.data.Result
import com.example.cycleme.databinding.ActivityUploadResultBinding
import com.example.cycleme.model.RecommendationRequest
import com.example.cycleme.model.RecommendationResponse
import com.example.cycleme.model.RecommendationResponseList

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
        adapter.notifyDataSetChanged()

        binding.rvRecommendation.layoutManager = LinearLayoutManager(this@UploadResultActivity)
        binding.rvRecommendation.adapter = adapter

    }

    private fun getRecommendation(category: String) {
        uploadResultViewModel.resultRecommendation.observe(this@UploadResultActivity) {
            when (it) {
                is Result.Success -> {
                    showToast("Recommendation Fetched")
                    val recommendationList: RecommendationResponseList? = it.data
                    recommendationList?.let {
                        // Recommendation list obtained successfully
                        // Pass it to the RecyclerView adapter
                        adapter.setData(it.recommendationResponseList!!)
                    }
                    showLoading(false)
                    finish()
                }
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Error -> {
                    showToast(it.error)
                    Log.e("UploadResutActivity ", it.error)
                    showLoading(false)
                }
            }
        }
        uploadResultViewModel.getRecommendation(RecommendationRequest(category))
    }

    private fun showToast(text: String) {
        Toast.makeText(this@UploadResultActivity, text, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        val progressBar = binding.progressBar
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.INVISIBLE
        }
    }
}