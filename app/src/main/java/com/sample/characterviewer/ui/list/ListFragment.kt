package com.sample.characterviewer.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.sample.characterviewer.R
import com.sample.characterviewer.databinding.FragmentItemListBinding
import com.sample.characterviewer.viewmodel.TopicsListViewModel

class ListFragment : Fragment() {

    private lateinit var viewModel: TopicsListViewModel
    private lateinit var adapter: TopicItemRecyclerViewAdapter

    private var _binding: FragmentItemListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.itemList

        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)

        setupRecyclerView(recyclerView, itemDetailFragmentContainer)

        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[TopicsListViewModel::class.java]
        viewModel.fetchAllTopics()

        viewModel.filteredTopicsListLiveData?.observe(this as LifecycleOwner) {
            if (it != null) {
                adapter.setData(it)
            }
        }

        viewModel.topicsListLiveData?.observe(this as LifecycleOwner, Observer {
            if (it != null) {
                viewModel.filterTopics(binding.edittextSearch?.text.toString() ?: "")
            }
        })

        binding.edittextSearch?.addTextChangedListener {
            viewModel.filterTopics(it.toString())
        }
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        itemDetailFragmentContainer: View?
    ) {
        adapter = TopicItemRecyclerViewAdapter(itemDetailFragmentContainer)
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}