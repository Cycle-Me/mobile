package com.example.cycleme.ui.main.auth.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cycleme.databinding.ActivityRegisterBinding
import com.example.cycleme.model.RegisterRequest
import com.example.cycleme.ui.main.auth.login.LoginActivity
import com.example.cycleme.utils.Result

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupListener()
        playAnimation()
        showLoading(false)
    }

    private fun setupListener() {
        binding.btnRegister.setOnClickListener {
            val inputName = binding.edRegisterName.text.toString()
            val inputEmail = binding.edRegisterEmail.text.toString()
            val inputPassword = binding.edRegisterPassword.text.toString()

            if (checkData(inputName, inputEmail, inputPassword)) {
                val user = RegisterRequest(inputName, inputEmail, inputPassword, inputPassword)
                post(user)
            } else {
                showToast("Data must be valid")
            }
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun post(user: RegisterRequest) {
        viewModel.postRegister(user).observe(this) {
            when (it) {
                is Result.Success -> {
                    showLoading(false)
                    showToast(it.data.msg)
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                }

                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showToast(it.error)
                    showLoading(false)
                }
            }
        }
    }

    private fun checkData(inputName: String, inputEmail: String, inputPassword: String): Boolean {
        if (inputName.isNotEmpty()) {
            if (inputEmail.contains("@") && inputEmail.contains(".com")) {
                if (inputPassword.length >= 8) {
                    return true
                }
            }
        }
        return false
    }

    private fun playAnimation() {
        val registerText =
            ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(500)
        val backButton =
            ObjectAnimator.ofFloat(binding.btnBack, View.ALPHA, 1f).setDuration(500)
        val image = ObjectAnimator.ofFloat(binding.ivAsset, View.ALPHA, 1f).setDuration(500)
        val imageBackground1 =
            ObjectAnimator.ofFloat(binding.ivAssetBackground1, View.ALPHA, 1f).setDuration(500)
        val imageBackground2 =
            ObjectAnimator.ofFloat(binding.ivAssetBackground2, View.ALPHA, 1f).setDuration(500)
        val nameText = ObjectAnimator.ofFloat(binding.tvFullName, View.ALPHA, 1f).setDuration(500)
        val name =
            ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1f).setDuration(500)
        val emailText = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(500)
        val email =
            ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(500)
        val passwordText =
            ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(500)
        val password =
            ObjectAnimator.ofFloat(binding.edRegisterPassword, View.ALPHA, 1f).setDuration(500)
        val btnRegister =
            ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)

        val togetherTitle = AnimatorSet().apply {
            playTogether(registerText, backButton)
        }

        val togetherImage = AnimatorSet().apply {
            playTogether(image, imageBackground1, imageBackground2)
        }

        val togetherName = AnimatorSet().apply {
            playTogether(nameText, name)
        }

        val togetherEmail = AnimatorSet().apply {
            playTogether(emailText, email)
        }

        val togetherPassword = AnimatorSet().apply {
            playTogether(passwordText, password)
        }

        AnimatorSet().apply {
            playSequentially(
                togetherTitle,
                togetherImage,
                togetherName,
                togetherEmail,
                togetherPassword,
                btnRegister
            )
            start()
        }
    }

    private fun setupUI() {
        // Hide action bar and status bar
        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}