package com.example.cycleme.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cycleme.databinding.ItemRewardsBinding
import com.example.cycleme.model.RewardItem
import com.example.cycleme.ui.main.detail_rewards.DetailRewardsActivity

class RewardsAdapter : ListAdapter<RewardItem, RewardsAdapter.RewardsViewHolder>(DIFF_CALLBACK) {

    fun setAllData(data: List<RewardItem>) {
        submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RewardsAdapter.RewardsViewHolder {
        val binding = ItemRewardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RewardsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RewardsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    inner class RewardsViewHolder(private var binding: ItemRewardsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(reward: RewardItem) {
            binding.apply {
                ivItemImage.setImageResource(reward.image)
                tvItemTitle.text = reward.title
                tvItemPoints.text = reward.points.toString() + " points"
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailRewardsActivity::class.java)
                val bundle = Bundle().apply {
                    putString("rewardDetail", reward.details)
                    putString("rewardTitle", reward.title)
                    putString("rewardPoint", reward.points.toString())
                    putInt("rewardBanner", reward.banner)
                }
                intent.putExtras(bundle)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RewardItem>() {
            override fun areItemsTheSame(oldItem: RewardItem, newItem: RewardItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RewardItem, newItem: RewardItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}