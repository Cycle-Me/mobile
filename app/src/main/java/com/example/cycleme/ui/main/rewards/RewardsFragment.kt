package com.example.cycleme.ui.main.rewards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.cycleme.R
import com.example.cycleme.adapters.RewardsAdapter
import com.example.cycleme.databinding.FragmentRewardsBinding
import com.example.cycleme.model.RewardItem

class RewardsFragment : Fragment() {
    private lateinit var binding: FragmentRewardsBinding
    private lateinit var adapter: RewardsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRewardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val itemList = getDataFromLocal()

        adapter = RewardsAdapter()
        adapter.setAllData(itemList)

        binding.rvRewards.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRewards.adapter = adapter
    }


    private fun getDataFromLocal(): MutableList<RewardItem> {
        val titles = resources.getStringArray(R.array.reward_title)
        val details = resources.getStringArray(R.array.reward_detail)
        val images = resources.obtainTypedArray(R.array.reward_image)
        val banners = resources.obtainTypedArray(R.array.reward_banner)
        val points = resources.getStringArray(R.array.reward_point)
        val list = mutableListOf<RewardItem>()
        titles.forEachIndexed { i, title ->
            val rewardItem = RewardItem(
                id = i,
                image = images.getResourceId(i, -1),
                banner = banners.getResourceId(i, -1),
                title = title,
                points = points[i].toInt(),
                details = details[i]
            )
            list.add(rewardItem)
        }
        images.recycle() // adding recycle() to free up its resources
        banners.recycle()
        return list
    }

    private fun setupListener() {
        binding.arrowButton.setOnClickListener {
            // If the CardView is already expanded, set its visibility to gone and change the expand less icon to expand more.
            if (binding.hiddenView.visibility == View.VISIBLE) {
                // The transition of the hiddenView is carried out by the TransitionManager class.
                // Here use an object of the AutoTransition Class to create a default transition
                TransitionManager.beginDelayedTransition(binding.baseCardview, AutoTransition())
                binding.hiddenView.visibility = View.GONE
                binding.arrowButton.setImageResource(R.drawable.ic_expand_20)
            } else {
                TransitionManager.beginDelayedTransition(binding.baseCardview, AutoTransition())
                binding.hiddenView.visibility = View.VISIBLE
                binding.arrowButton.setImageResource(R.drawable.ic_collapse_20)
            }
        }
    }

}