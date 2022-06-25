package com.yihsiang.zoo.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.yihsiang.zoo.R
import com.yihsiang.zoo.model.Animal
import com.yihsiang.zoo.model.AnimalDetail
import com.yihsiang.zoo.model.AnimalDetailItem
import com.yihsiang.zoo.model.AnimalThumbnailHeader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AnimalDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val details = savedStateHandle.getLiveData<Animal>("animal")
        .asFlow()
        .map { animal ->
            mutableListOf<AnimalDetailItem>()
                .apply {
                    add(AnimalThumbnailHeader(animal.thumbnail))
                    add(AnimalDetail(title = animal.name, content = animal.enName))
                    if (animal.alsoKnown.isNotEmpty()) {
                        add(AnimalDetail(titleResource = R.string.also_known, content = animal.alsoKnown))
                    }
                    add(AnimalDetail(titleResource = R.string.description, content = animal.description))
                    add(AnimalDetail(titleResource = R.string.feature, content = animal.feature))
                    add(AnimalDetail(titleResource = R.string.behavior, content = animal.behavior))
                }
        }
}