package com.yihsiang.zoo.model

import com.google.gson.annotations.SerializedName

data class CommonResponse<T>(@SerializedName("result") val entity: CommonEntity<T>)

data class CommonEntity<T>(val results: List<T>)

val <T> CommonResponse<T>.data
    get() = entity.results