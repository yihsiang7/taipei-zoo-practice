package com.yihsiang.zoo.ui.venue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yihsiang.zoo.R
import com.yihsiang.zoo.databinding.FragmentZooVenueBinding
import com.yihsiang.zoo.ui.venue.ZooVenueFragmentDirections.Companion.toAnimal
import com.yihsiang.zoo.utils.eventObserve
import com.yihsiang.zoo.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ZooVenueFragment : Fragment() {

    private var _binding: FragmentZooVenueBinding? = null
    private val binding: FragmentZooVenueBinding
        get() = _binding!!

    private val viewModel by viewModels<ZooVenueViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentZooVenueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ZooVenueAdapter(viewModel)
        with(binding.venues) {
            this.adapter = adapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }

        viewModel.navigateToAnimalAction.eventObserve(viewLifecycleOwner) {
            findNavController().navigate(toAnimal(it.name, it))
        }

        lifecycleScope.launch {
            viewModel.venues.collect { result ->
                if (result.isSuccess) {
                    adapter.submitList(result.getOrElse { emptyList() })
                } else {
                    toast(R.string.error_occurred)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}