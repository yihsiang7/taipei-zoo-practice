package com.yihsiang.zoo.ui.animal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yihsiang.zoo.R
import com.yihsiang.zoo.databinding.ItemAnimalBinding
import com.yihsiang.zoo.databinding.ItemAnimalHeaderBinding
import com.yihsiang.zoo.databinding.ItemVenueDescriptionBinding
import com.yihsiang.zoo.model.Animal
import com.yihsiang.zoo.model.AnimalHeader
import com.yihsiang.zoo.model.AnimalItem
import com.yihsiang.zoo.model.VenueDescriptionHeader

class AnimalAdapter(
    private val eventListener: AnimalEventListener
) : ListAdapter<AnimalItem, RecyclerView.ViewHolder>(AnimalDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_venue_description ->
                VenueDescriptionViewHolder(
                    ItemVenueDescriptionBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            R.layout.item_animal_header ->
                AnimalHeaderViewHolder(
                    ItemAnimalHeaderBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            R.layout.item_animal ->
                AnimalViewHolder(
                    ItemAnimalBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            else -> throw IllegalStateException("Unknown ViewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VenueDescriptionViewHolder -> holder.bind(getItem(position) as VenueDescriptionHeader)
            is AnimalViewHolder -> holder.bind(getItem(position) as Animal, eventListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (val result = getItem(position)) {
            is VenueDescriptionHeader -> R.layout.item_venue_description
            is AnimalHeader -> R.layout.item_animal_header
            is Animal -> R.layout.item_animal
            else -> throw IllegalStateException("Unknown AnimalItem: $$result")
        }
    }
}

class VenueDescriptionViewHolder(
    private val binding: ItemVenueDescriptionBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(header: VenueDescriptionHeader) {
        binding.venue = header.venue
        binding.executePendingBindings()
    }
}

class AnimalHeaderViewHolder(
    binding: ItemAnimalHeaderBinding
) : RecyclerView.ViewHolder(binding.root)

class AnimalViewHolder(
    private val binding: ItemAnimalBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(_animal: Animal, _eventListener: AnimalEventListener) {
        with(binding) {
            animal = _animal
            eventListener = _eventListener
            executePendingBindings()
        }
    }
}

object AnimalDiffCallback : DiffUtil.ItemCallback<AnimalItem>() {

    override fun areItemsTheSame(oldItem: AnimalItem, newItem: AnimalItem): Boolean =
        when {
            oldItem is Animal && newItem is Animal -> oldItem.id == newItem.id
            else -> true
        }

    override fun areContentsTheSame(oldItem: AnimalItem, newItem: AnimalItem): Boolean =
        when {
            oldItem is Animal && newItem is Animal -> oldItem == newItem
            else -> true
        }
}