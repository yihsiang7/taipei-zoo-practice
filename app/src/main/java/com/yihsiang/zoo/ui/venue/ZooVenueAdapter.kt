package com.yihsiang.zoo.ui.venue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yihsiang.zoo.databinding.ItemZooVenueBinding
import com.yihsiang.zoo.model.Venue

class ZooVenueAdapter(
    private val venueEventListener: VenueEventListener
) : ListAdapter<Venue, ZooVenueViewHolder>(ZooVenueDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZooVenueViewHolder {
        return ZooVenueViewHolder(
            ItemZooVenueBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ZooVenueViewHolder, position: Int) {
        holder.bind(getItem(position), venueEventListener)
    }
}

class ZooVenueViewHolder(private val binding: ItemZooVenueBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind(_venue: Venue, _eventListener: VenueEventListener) {
            with(binding) {
                venue = _venue
                eventListener = _eventListener
                executePendingBindings()
            }
            binding.venue = _venue
            binding.executePendingBindings()
        }
    }

object ZooVenueDiffCallback : DiffUtil.ItemCallback<Venue>() {
    override fun areItemsTheSame(oldItem: Venue, newItem: Venue): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Venue, newItem: Venue): Boolean =
        oldItem == newItem
}