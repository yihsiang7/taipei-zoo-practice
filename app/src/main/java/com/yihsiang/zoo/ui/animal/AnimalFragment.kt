package com.yihsiang.zoo.ui.animal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yihsiang.zoo.R
import com.yihsiang.zoo.databinding.FragmentAnimalBinding
import com.yihsiang.zoo.ui.animal.AnimalFragmentDirections.Companion.toAnimalDetail
import com.yihsiang.zoo.utils.eventObserve
import com.yihsiang.zoo.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimalFragment : Fragment() {

    private var _binding: FragmentAnimalBinding? = null
    private val binding: FragmentAnimalBinding
        get() = _binding!!

    private val viewModel by viewModels<AnimalViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AnimalAdapter(viewModel)
        with(binding.animals) {
            this.adapter = adapter
            addItemDecoration(AnimalItemDecoration(requireContext()))
        }

        lifecycleScope.launch {
            viewModel.animals.collect { result ->
                if (result.isSuccess) {
                    adapter.submitList(result.getOrElse { emptyList() })
                } else {
                    toast(R.string.error_occurred)
                }
            }
        }

        viewModel.navigateToAnimalDetailAction.eventObserve(viewLifecycleOwner) {
            findNavController().navigate(toAnimalDetail(it.name, it))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}