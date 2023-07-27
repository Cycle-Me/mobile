package com.example.cycleme.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cycleme.databinding.ItemStoryBinding
import com.example.cycleme.model.StoriesResponse
import com.example.cycleme.ui.main.detail_story.DetailStoryActivity

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    private var data: List<StoriesResponse> = emptyList()

    private var onItemClickCallback: OnItemClickCallBack? = null

    interface OnItemClickCallBack {
        fun onItemClicked(data: StoriesResponse)
    }

    fun setData(newData: List<StoriesResponse>) {
        data = newData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryAdapter.ViewHolder {
        val view = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StoriesResponse) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(item)

                val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                intent.putExtra("detailStory", item)
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.ivStory, "profile"),
                        Pair(binding.tvUsername, "name"),
                        Pair(binding.tvDescription, "description")
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }

            if (item.attachment != "") {
                Glide.with(itemView)
                    .load(item.attachment)
                    .centerCrop()
                    .into(binding.ivStory)
            }

            binding.tvUsername.text = item.user?.name ?: "Someone"
            binding.tvDescription.text = item.description
        }
    }

}
