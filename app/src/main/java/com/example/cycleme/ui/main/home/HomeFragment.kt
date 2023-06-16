package com.example.cycleme.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cycleme.R
import com.example.cycleme.adapters.ArticleAdapter
import com.example.cycleme.model.Article
import com.example.cycleme.ui.main.detail_article.DetailArticleActivity

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.rv_article)

        val list = getPlaceList()
        initRecyclerView(list)

        return view
    }

    private fun initRecyclerView(list: MutableList<Article>) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = ArticleAdapter(list, onClick = { article: Article ->
            val intent = Intent(requireContext(), DetailArticleActivity::class.java)
            intent.putExtra(EXTRA_ARTICLE, article)

            startActivity(intent)
        })

    }

    private fun getPlaceList(): MutableList<Article> {
        val titles = resources.getStringArray(R.array.titles)
        val descriptions = resources.getStringArray(R.array.descriptions)
        val images = resources.obtainTypedArray(R.array.images)
        val list = mutableListOf<Article>()
        titles.forEachIndexed { i, title ->
            val article = Article(
                title = title,
                description = descriptions[i],
                image = images.getResourceId(i, -1),
            )
            list.add(article)
        }
        images.recycle() // adding recycle() to free up its resources
        return list
    }

    companion object {
        const val EXTRA_ARTICLE = "EXTRA_ARTICLE"
    }
}