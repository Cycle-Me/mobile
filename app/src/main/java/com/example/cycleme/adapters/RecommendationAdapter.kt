package com.example.cycleme.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.cycleme.R
import com.example.cycleme.model.RecommendationResponse

class RecommendationAdapter : RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {
    private var data: List<RecommendationResponse?> = emptyList()

    private var onItemClickCallback: OnItemClickCallBack? = null

    interface OnItemClickCallBack {
        fun onItemClicked(data: RecommendationResponse)
    }

    fun setData(newData: List<RecommendationResponse?>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recommendation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        item?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivImage: ImageView = itemView.findViewById(R.id.iv_image)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)

        private val circularProgressDrawable = CircularProgressDrawable(itemView.context).apply {
            strokeWidth = 5f
            centerRadius = 25f
        }

        fun bind(item: RecommendationResponse) {
            circularProgressDrawable.start() // Loading placeholder for Glide

            Glide.with(itemView.context)
                .load(item.image)
                .placeholder(circularProgressDrawable)
                .into(ivImage)

            tvTitle.text = item.name

            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(item)

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(item.desc)
                itemView.context.startActivity(intent)
            }
        }
    }
}
