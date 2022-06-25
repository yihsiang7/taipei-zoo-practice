package com.yihsiang.zoo.model


sealed class AnimalDetailItem

data class AnimalThumbnailHeader(val thumbnail: String) : AnimalDetailItem()

data class AnimalDetail(
    val titleResource: Int = -1,
    val title: String = "",
    val content: String
) : AnimalDetailItem()