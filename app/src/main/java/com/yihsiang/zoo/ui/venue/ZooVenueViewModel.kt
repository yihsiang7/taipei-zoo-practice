package com.yihsiang.zoo.ui.venue

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yihsiang.zoo.model.Venue
import com.yihsiang.zoo.model.ZooRepository
import com.yihsiang.zoo.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ZooVenueViewModel @Inject constructor(
    zooRepository: ZooRepository
) : ViewModel(), VenueEventListener {

    val venues = zooRepository.getVenues()

    private val _navigateToAnimalAction = MutableLiveData<Event<Venue>>()
    val navigateToAnimalAction: LiveData<Event<Venue>>
        get() = _navigateToAnimalAction

    override fun navigateToAnimal(venue: Venue) {
        _navigateToAnimalAction.value = Event(venue)
    }
}

interface VenueEventListener {
    fun navigateToAnimal(venue: Venue)
}