package com.example.cycleme.ui.main.change_profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cycleme.databinding.ActivityChangeProfileBinding
import com.example.cycleme.repository.local.SessionPreferences
import com.example.cycleme.utils.Result

class ChangeProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeProfileBinding
    private lateinit var mSessionPreferences: SessionPreferences
    private val viewModel by viewModels<ChangeProfileViewModel>()
    private var isEditModeEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mSessionPreferences = SessionPreferences(this)

        setupListener()
        loadUserData()
        showLoading(false)
    }

    private fun loadUserData() {
        val getSession = mSessionPreferences.getLogin()
        binding.edFullName.setText(getSession.name)
        binding.edEmail.setText(getSession.email)
        binding.edPassword.setText(getSession.password)
    }

    private fun setupListener() {
        binding.ibBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnSave.setOnClickListener {
            updateData()
        }

        binding.btnEditProfile.setOnClickListener {
            isEditModeEnabled = !isEditModeEnabled
            updateButtonState(isEditModeEnabled) // Toggle the state
        }
    }

    private fun updateData() {
        showLoading(true)

        val getSession = mSessionPreferences.getLogin()
        val userId = getSession.uuid.toString()
        val updatedName = binding.edFullName.text.toString()
        val updatedEmail = binding.edEmail.text.toString()
        val updatedPassword = binding.edPassword.text.toString()

        viewModel.changeProfile(userId, updatedName, updatedEmail, updatedPassword, updatedPassword)

        viewModel.changeProfileResult.observe(this) {
            when (it) {
                is Result.Success -> {
                    showLoading(false)
                    showToast("Profile updated")
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showToast(it.error)
                    showLoading(false)
                }
            }
        }
    }

    private fun updateButtonState(state: Boolean) {
        binding.edFullName.isEnabled = state
        binding.edEmail.isEnabled = state
        binding.edPassword.isEnabled = state
        binding.btnSave.isEnabled = state
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}