package com.example.cycleme.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cycleme.databinding.ItemArticleBinding
import com.example.cycleme.model.Article

class ArticleAdapter(
    val list: List<Article>? = emptyList(),
    private val onClick: ((Article) -> Unit)? = null
) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.orEmpty().size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(var binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val place = list?.get(position)
            binding.ivArticle.setImageResource(place?.image ?: -1)
            binding.tvTitle.text = place?.title
            binding.root.setOnClickListener {
                if (place != null) {
                    onClick?.invoke(place)
                }
            }
        }
    }
}