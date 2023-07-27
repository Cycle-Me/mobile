package com.example.cycleme.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cycleme.databinding.ItemRecycleBinding
import com.example.cycleme.model.RecycleItem
import com.example.cycleme.ui.main.detail_recycle.DetailRecycleActivity
import com.example.cycleme.ui.main.upload_photo.UploadPhotoActivity

class RecycleAdapter :
    ListAdapter<RecycleItem, RecycleAdapter.RecycleViewHolder>(DIFF_CALLBACK) {

    fun setAllData(data: List<RecycleItem>) {
        submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val binding = ItemRecycleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecycleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    inner class RecycleViewHolder(private var binding: ItemRecycleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recycle: RecycleItem) {
            binding.apply {
                ibPicture.setImageResource(recycle.image)
                tvDesc.text = recycle.desc
                tvLabel.text = recycle.label
            }

            // Make image and title clickable
            itemView.setOnClickListener(onItemClickListener(recycle))
            binding.ibPicture.setOnClickListener(onItemClickListener(recycle))
        }

        private fun onItemClickListener(recycle: RecycleItem): (View) -> Unit = { view ->
            val intent = if (recycle.id == 0) {
                Intent(view.context, UploadPhotoActivity::class.java)
            } else {
                Intent(view.context, DetailRecycleActivity::class.java).apply {
                    putExtra("label", recycle.label)
                }
            }
            view.context.startActivity(intent)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecycleItem>() {
            override fun areItemsTheSame(oldItem: RecycleItem, newItem: RecycleItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RecycleItem, newItem: RecycleItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}

