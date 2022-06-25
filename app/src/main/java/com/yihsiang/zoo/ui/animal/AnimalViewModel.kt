package com.yihsiang.zoo.ui.animal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.yihsiang.zoo.model.Animal
import com.yihsiang.zoo.model.AnimalHeader
import com.yihsiang.zoo.model.AnimalItem
import com.yihsiang.zoo.model.Venue
import com.yihsiang.zoo.model.VenueDescriptionHeader
import com.yihsiang.zoo.model.ZooRepository
import com.yihsiang.zoo.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AnimalViewModel @Inject constructor(
    zooRepository: ZooRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(), AnimalEventListener {

    private val _navigateToAnimalDetailAction = MutableLiveData<Event<Animal>>()
    val navigateToAnimalDetailAction: LiveData<Event<Animal>>
        get() = _navigateToAnimalDetailAction

    @OptIn(FlowPreview::class)
    val animals = savedStateHandle.getLiveData<Venue>("venue")
        .asFlow()
        .flatMapConcat { venue ->
            zooRepository.getAnimalsBy(venue.name)
                .map { result ->
                    if (result.isSuccess) {
                        val items = result.getOrElse { emptyList() }
                            .toMutableList<AnimalItem>()
                            .apply {
                                val hasAnimals = isNotEmpty()
                                add(0, VenueDescriptionHeader(venue))
                                if (hasAnimals) {
                                    add(1, AnimalHeader)
                                }
                            }
                        return@map Result.success(items)
                    }
                    result
                }
        }

    override fun navigateToAnimalDetail(animal: Animal) {
        _navigateToAnimalDetailAction.value = Event(animal)
    }
}

interface AnimalEventListener {
    fun navigateToAnimalDetail(animal: Animal)
}