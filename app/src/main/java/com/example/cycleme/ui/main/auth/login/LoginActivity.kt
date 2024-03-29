package com.example.cycleme.ui.main.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cycleme.databinding.ActivityLoginBinding
import com.example.cycleme.model.LoginDataOnSession
import com.example.cycleme.model.LoginRequest
import com.example.cycleme.model.LoginResult
import com.example.cycleme.repository.local.SessionPreferences
import com.example.cycleme.ui.main.MainActivity
import com.example.cycleme.ui.main.auth.register.RegisterActivity
import com.example.cycleme.utils.Result

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionPreferences: SessionPreferences
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupListener()
        playAnimation()
        showLoading(false)
    }


    private fun playAnimation() {
        val logo = ObjectAnimator.ofFloat(binding.ivLogo, View.ALPHA, 1f).setDuration(500)
        val image2 = ObjectAnimator.ofFloat(binding.imageView2, View.ALPHA, 1f).setDuration(500)
        val image3 = ObjectAnimator.ofFloat(binding.imageView3, View.ALPHA, 1f).setDuration(500)
        val image4 = ObjectAnimator.ofFloat(binding.imageView4, View.ALPHA, 1f).setDuration(500)
        val image5 = ObjectAnimator.ofFloat(binding.imageView5, View.ALPHA, 1f).setDuration(500)
        val emailText = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.tilEmail, View.ALPHA, 1f).setDuration(500)
        val passwordText =
            ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.tilPassword, View.ALPHA, 1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val notRegisterYet =
            ObjectAnimator.ofFloat(binding.tvNotRegisterYet, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(500)

        val togetherImage = AnimatorSet().apply {
            playTogether(logo, image2, image3, image4, image5)
        }

        val togetherEmail = AnimatorSet().apply {
            playTogether(emailText, email)
        }

        val togetherPassword = AnimatorSet().apply {
            playTogether(passwordText, password)
        }

        val togetherRegisterText = AnimatorSet().apply {
            playTogether(notRegisterYet, register)
        }

        AnimatorSet().apply {
            playSequentially(
                togetherImage,
                togetherEmail,
                togetherPassword,
                btnLogin,
                togetherRegisterText
            )
            start()
        }
    }

    private fun setupUI() {
        // Hide action bar and status bar
        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun setupListener() {
        binding.btnLogin.setOnClickListener {
            val inputEmail = binding.edLoginEmail.text.toString()
            val inputPassword = binding.edLoginPassword.text.toString()
            val request = LoginRequest(inputEmail, inputPassword)

            if (checkData(inputEmail, inputPassword)) {
                post(request)
            } else {
                showToast("Email or password not valid")
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun post(request: LoginRequest) {
        showLoading(true)

        viewModel.postLogin(request)

        viewModel.resultLoginResponse.observe(this) { result ->
            showLoading(false)

            when (result) {
                is Result.Success -> {
                    showToast("Login Success")
                    if (result.data.msg.toString() == "success") {
                        saveLogin(result.data.loginResult!!, request.password.toString())
                        toMainActivity(result.data.loginResult)
                    }
                    finish()
                }

                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showToast(result.error)
                }
            }
        }
    }

    private fun toMainActivity(result: LoginResult) {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.putExtra("result", result)
        startActivity(intent)
        finish()
    }

    private fun saveLogin(loginResult: LoginResult, password: String) {
        sessionPreferences = SessionPreferences(this)
        sessionPreferences.setLogin(
            LoginDataOnSession(
                true,
                loginResult.uuid,
                loginResult.name,
                loginResult.email,
                loginResult.sessionID,
                password
            )
        )
    }

    private fun checkData(email: String, password: String): Boolean {
        if (email.contains("@") && email.contains(".com")) {
            if (password.length >= 8) {
                return true
            }
        }
        return false
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}