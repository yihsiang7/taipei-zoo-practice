package com.yihsiang.zoo.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yihsiang.zoo.R
import com.yihsiang.zoo.databinding.ItemAnimalDetailBinding
import com.yihsiang.zoo.databinding.ItemAnimalDetailThumbnailHeaderBinding
import com.yihsiang.zoo.model.AnimalDetail
import com.yihsiang.zoo.model.AnimalDetailItem
import com.yihsiang.zoo.model.AnimalThumbnailHeader

class AnimalDetailAdapter : ListAdapter<AnimalDetailItem, RecyclerView.ViewHolder>(AnimalDetailDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when  (viewType) {
            R.layout.item_animal_detail_thumbnail_header ->
                AnimalThumbnailHeaderViewHolder(
                    ItemAnimalDetailThumbnailHeaderBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            R.layout.item_animal_detail ->
                AnimalDetailViewHolder(
                    ItemAnimalDetailBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            else -> throw IllegalStateException("Unknown ViewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AnimalThumbnailHeaderViewHolder ->
                holder.bind(getItem(position) as AnimalThumbnailHeader)
            is AnimalDetailViewHolder ->
                holder.bind(getItem(position) as AnimalDetail)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (val result = getItem(position)) {
            is AnimalThumbnailHeader -> R.layout.item_animal_detail_thumbnail_header
            is AnimalDetail -> R.layout.item_animal_detail
            else -> throw IllegalStateException("Unknown AnimalDetailItem: $$result")
        }
    }
}

object AnimalDetailDiffCallback : DiffUtil.ItemCallback<AnimalDetailItem>() {
    override fun areItemsTheSame(oldItem: AnimalDetailItem, newItem: AnimalDetailItem): Boolean =
        true

    override fun areContentsTheSame(oldItem: AnimalDetailItem, newItem: AnimalDetailItem): Boolean =
        true
}

class AnimalThumbnailHeaderViewHolder(
    private val binding: ItemAnimalDetailThumbnailHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(header: AnimalThumbnailHeader) {
        binding.header = header
        binding.executePendingBindings()
    }
}

class AnimalDetailViewHolder(
    private val binding: ItemAnimalDetailBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(detail: AnimalDetail) {
        binding.detail = detail
        binding.executePendingBindings()
    }
}