package com.example.cycleme.ui.main.detail_article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cycleme.databinding.ActivityDetailArticleBinding
import com.example.cycleme.model.Article
import com.example.cycleme.ui.main.home.HomeFragment.Companion.EXTRA_ARTICLE

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val article: Article? = intent.getParcelableExtra(EXTRA_ARTICLE)

        if (article != null) {
            binding.tvTitle.text = article.title
            binding.imgDetailImage.setImageResource(article.image)
            binding.tvDescription.text = article.description
        }
    }
}