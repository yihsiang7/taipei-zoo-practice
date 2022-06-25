package com.yihsiang.zoo.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ZooRepository(
    private val zooService: ZooService,
    private val animalJsonDataSource: LocalAnimalJsonDataSource
) {

    fun getVenues(): Flow<Result<List<Venue>>> =
        flow { emit(Result.success(zooService.getVenues(VENUE_URL).data)) }
            .catch { emit(Result.failure(it)) }

    fun getAnimalsBy(location: String): Flow<Result<List<Animal>>> =
        flow {
            val animals = animalJsonDataSource.getAnimals()
                .filter { location.contains(it.location) }
            emit(Result.success(animals))
        }
        .catch { emit(Result.failure(it)) }
}
