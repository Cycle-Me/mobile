package com.example.cycleme.ui.main.home

//import androidx.navigation.fragment.findNavController
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.cycleme.R
import com.example.cycleme.adapters.ArticleAdapter
import com.example.cycleme.adapters.ImagePagerAdapter
import com.example.cycleme.databinding.FragmentHomeBinding
import com.example.cycleme.model.Article
import com.example.cycleme.repository.local.SessionPreferences
import com.example.cycleme.ui.main.detail_article.DetailArticleActivity
import com.example.cycleme.ui.main.detail_recycle.DetailRecycleActivity
import com.example.cycleme.ui.main.notification.NotificationActivity
import com.example.cycleme.ui.main.share.ShareActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var mSessionPreferences: SessionPreferences

    private val images = listOf(
        R.drawable.carousel_2,
        R.drawable.carousel_1,
        R.drawable.carousel_3
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvArticle
        val list = getPlaceList()
        initRecyclerView(list)

        setUsername()
        setupCarousel()
        setupListener()
    }

    private fun setupCarousel() {
        val adapter = ImagePagerAdapter(requireContext(), images)
        binding.viewPager2.adapter = adapter

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDots(position)
            }
        })

        addDots(binding.dotsLayout, images.size)
        updateDots(0)
    }

    private fun addDots(dotsLayout: LinearLayout, count: Int) {
        val dots = arrayOfNulls<ImageView>(count)

        for (i in 0 until count) {
            dots[i] = ImageView(requireContext())
            dots[i]?.setImageResource(R.drawable.ic_dot_unselected)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 16, 8, 4)
            dotsLayout.addView(dots[i], params)
        }
    }

    private fun updateDots(currentPosition: Int) {
        val dotsLayout = binding.dotsLayout
        val dotsCount = dotsLayout.childCount

        for (i in 0 until dotsCount) {
            val dot = dotsLayout.getChildAt(i) as ImageView
            dot.setImageResource(
                if (i == currentPosition) R.drawable.ic_dot_selected else R.drawable.ic_dot_unselected
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUsername() {
        mSessionPreferences = SessionPreferences(requireContext())
        val getSession = mSessionPreferences.getLogin()
        binding.tvName.text = " ${getSession.name}"
    }

    private fun initRecyclerView(list: MutableList<Article>) {
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = ArticleAdapter(list, onClick = { article: Article ->
            val intent = Intent(requireContext(), DetailArticleActivity::class.java)
            intent.putExtra(EXTRA_ARTICLE, article)
            startActivity(intent)
        })

    }

    private fun setupListener() {
        binding.ivNotification.setOnClickListener {
            val intent = Intent(requireContext(), NotificationActivity::class.java)
            startActivity(intent)
        }

        binding.btnShare.setOnClickListener {
            val intent = Intent(requireContext(), ShareActivity::class.java)
            startActivity(intent)
        }

        binding.btnPopular1.setOnClickListener {
            val intent = Intent(requireContext(), DetailRecycleActivity::class.java)
            intent.putExtra("label", "Plastic")
            startActivity(intent)
        }

        binding.btnPopular2.setOnClickListener {
            val intent = Intent(requireContext(), DetailRecycleActivity::class.java)
            intent.putExtra("label", "Textiles")
            startActivity(intent)
        }

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