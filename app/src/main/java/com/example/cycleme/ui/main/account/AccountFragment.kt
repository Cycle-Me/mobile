package com.example.cycleme.ui.main.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cycleme.databinding.FragmentAccountBinding
import com.example.cycleme.repository.local.SessionPreferences
import com.example.cycleme.ui.main.auth.login.LoginActivity
import com.example.cycleme.ui.main.change_profile.ChangeProfileActivity
import com.example.cycleme.ui.main.faq.FAQActivity
import com.example.cycleme.ui.main.send_feedback.SendFeedbackActivity
import com.example.cycleme.utils.Result

class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    private lateinit var mSessionPreferences: SessionPreferences
    private val viewModel by viewModels<AccountViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
        setUsername()
        showLoading(false)
    }

    private fun setUsername() {
        mSessionPreferences = SessionPreferences(requireContext())
        val getSession = mSessionPreferences.getLogin()
        binding.tvName.text = getSession.name
    }

    private fun logout() {
        showLoading(true)

        viewModel.postLogout()

        viewModel.resultLogoutResponse.observe(viewLifecycleOwner) { result ->
            showLoading(false)

            when (result) {
                is Result.Success -> {
                    mSessionPreferences = SessionPreferences(requireContext())
                    mSessionPreferences.clearSession()
                    showToast(result.data.msg.toString())
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                    // Remove the current Fragment from its host Activity
                    parentFragmentManager.beginTransaction().remove(this).commit()
                }

                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showToast(result.error)
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setupListener() {
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext()).setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ -> logout() }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        binding.btnChangeProfile.setOnClickListener {
            val intent = Intent(requireContext(), ChangeProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnFaq.setOnClickListener {
            val intent = Intent(requireContext(), FAQActivity::class.java)
            startActivity(intent)
        }

        binding.btnSendFeedback.setOnClickListener {
            val intent = Intent(requireContext(), SendFeedbackActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

}
