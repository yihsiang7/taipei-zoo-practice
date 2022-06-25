package com.yihsiang.zoo.model

import retrofit2.http.GET
import retrofit2.http.Url

interface ZooService {

    @GET
    suspend fun getVenues(@Url url: String): CommonResponse<Venue>
}

const val VENUE_URL = "https://data.taipei/api/v1/dataset/5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a?scope=resourceAquire"