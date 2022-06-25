package com.yihsiang.zoo.ui.detail

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.yihsiang.zoo.databinding.FragmentAnimalDetailBinding
import com.yihsiang.zoo.utils.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimalDetailFragment : Fragment() {

    private var _binding: FragmentAnimalDetailBinding? = null
    private val binding: FragmentAnimalDetailBinding
        get() = _binding!!

    private val viewModel by viewModels<AnimalDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimalDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AnimalDetailAdapter()
        binding.contents.adapter = adapter
        binding.contents.addItemDecoration(createItemDecoration())

        lifecycleScope.launch {
            viewModel.details.collect { adapter.submitList(it) }
        }
    }

    private fun createItemDecoration(): RecyclerView.ItemDecoration =
        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                when (parent.getChildAdapterPosition(view)) {
                    0 -> {
                        outRect.bottom = 8.dp
                    }
                    parent.adapter?.itemCount?.minus(1) -> {
                        outRect.top = 8.dp
                        outRect.bottom = 16.dp
                    }
                    else -> {
                        outRect.top = 8.dp
                        outRect.bottom = 8.dp
                    }
                }
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}