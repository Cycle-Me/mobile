package com.example.cycleme.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cycleme.R
import com.example.cycleme.databinding.ItemRecommendationBinding
import com.example.cycleme.model.RecommendationResponse
import com.squareup.picasso.Picasso

//class RecommendationAdapter(
//    val list: List<RecommendationResponse>? = emptyList(),
//    private val onClick: ((RecommendationResponse) -> Unit)? = null
//) : RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding = ItemRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding)
//    }
//
//    override fun getItemCount(): Int = list.orEmpty().size
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(position)
//    }
//
//    inner class ViewHolder(var binding: ItemRecommendationBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(position: Int) {
//            val item = list?.get(position)
//            binding.ivImage.setImageResource((item?.image ?: -1) as Int)
//            binding.tvTitle.text = item?.name
//            binding.root.setOnClickListener {
//                if (item != null) {
//                    onClick?.invoke(item)
//                }
//            }
//        }
//    }
//}

//class RecommendationAdapter : RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {
//    private var data: List<RecommendationResponse> = emptyList()
//
//    private var onItemClickCallback: OnItemClickCallBack? = null
//
//    interface OnItemClickCallBack {
//        fun onItemClicked(data: RecommendationResponse)
//    }
//
//    fun setData(newData: List<RecommendationResponse>) {
//        data = newData
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_recommendation, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(data[position])
//    }
//
//    override fun getItemCount(): Int = data.size
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val ivImage: ImageView = itemView.findViewById(R.id.iv_image)
//        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
//
//        fun bind(item: RecommendationResponse) {
//            Picasso.get().load(item.image).into(ivImage)
//            tvTitle.text = item.name
//
//            itemView.setOnClickListener {
//                onItemClickCallback?.onItemClicked(item)
//
//                val intent = Intent(Intent.ACTION_VIEW)
//                intent.data = Uri.parse("https://www.youtube.com")
//                itemView.context.startActivity(intent)
//            }
//        }
//    }
//}

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

        fun bind(item: RecommendationResponse) {
            ivImage.setImageResource(item.image?.toIntOrNull() ?: -1)
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
