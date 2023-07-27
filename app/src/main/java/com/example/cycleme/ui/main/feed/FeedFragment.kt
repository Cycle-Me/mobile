package com.example.cycleme.ui.main.feed

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cycleme.adapters.StoryAdapter
import com.example.cycleme.databinding.FragmentFeedBinding
import com.example.cycleme.ui.main.upload_photo_feed.UploadPhotoFeedActivity
import com.example.cycleme.utils.Result

class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var adapter: StoryAdapter
    private val viewModel by viewModels<FeedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupListener()
        getStory()
    }

    private fun setupListener() {
        binding.fabAddStory.setOnClickListener {
            val intent = Intent(requireContext(), UploadPhotoFeedActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        adapter = StoryAdapter()

        binding.rvStory.adapter = adapter
        binding.rvStory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun getStory() {
        viewModel.getAllStories()

        viewModel.responseStories.observe(viewLifecycleOwner) {
            showLoading(true)

            when (it) {
                is Result.Success -> {
                    adapter.setData(it.data)
                    showLoading(false)
                }

                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Error -> {
                    showToast(it.error)
                    Log.e("FeedFragment ", it.error)
                    showLoading(false)
                }
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

}