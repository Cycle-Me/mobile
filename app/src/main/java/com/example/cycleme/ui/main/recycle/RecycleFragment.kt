package com.example.cycleme.ui.main.recycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cycleme.R
import com.example.cycleme.adapters.RecycleAdapter
import com.example.cycleme.databinding.FragmentRecycleBinding
import com.example.cycleme.model.RecycleItem

class RecycleFragment : Fragment() {

    private lateinit var adapter: RecycleAdapter
    private lateinit var binding: FragmentRecycleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecycleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val itemList = getDataFromLocal()

        adapter = RecycleAdapter()
        adapter.setAllData(itemList)

        binding.rvRecycle.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvRecycle.adapter = adapter
    }

    private fun getDataFromLocal(): MutableList<RecycleItem> {
        val labels = resources.getStringArray(R.array.recycle_label)
        val descriptions = resources.getStringArray(R.array.recycle_desc)
        val images = resources.obtainTypedArray(R.array.recycle_image)
        val list = mutableListOf<RecycleItem>()
        labels.forEachIndexed { i, label ->
            val recycleItem = RecycleItem(
                id = i,
                image = images.getResourceId(i, -1),
                label = label,
                desc = descriptions[i],
            )
            list.add(recycleItem)
        }
        images.recycle() // adding recycle() to free up its resources
        return list
    }
}