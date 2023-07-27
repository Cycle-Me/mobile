package com.example.cycleme.ui.main.send_feedback

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cycleme.databinding.ActivitySendFeedbackBinding
import com.example.cycleme.repository.local.SessionPreferences
import com.example.cycleme.utils.Result

class SendFeedbackActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySendFeedbackBinding
    private lateinit var mSessionPreferences: SessionPreferences
    private val viewModel by viewModels<SendFeedbackViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
        showLoading(false)
    }

    private fun setupListener() {
        binding.ibBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnSend.setOnClickListener {
            if (binding.edFeedbackTitle.text.isNotEmpty() && binding.edFeedbackDetails.text.isNotEmpty()) {
                sendFeedback()
            } else {
                showToast("Title and feedback section must be filled")
            }
        }
    }

    private fun sendFeedback() {
        showLoading(true)

        mSessionPreferences = SessionPreferences(this)
        val getSession = mSessionPreferences.getLogin()
        val senderEmail = getSession.email.toString()
        val feedbackTitle = binding.edFeedbackTitle.text.toString()
        val feedbackDetails = binding.edFeedbackDetails.text.toString()

        viewModel.sendFeedback(senderEmail, feedbackTitle, feedbackDetails)

        viewModel.feedbackResult.observe(this) {
            when (it) {
                is Result.Success -> {
                    showLoading(false)
                    binding.edFeedbackTitle.text.clear()
                    binding.edFeedbackDetails.text.clear()
                    showToast("Feedback sent")
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showToast(it.error)
                    showLoading(false)
                }
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}