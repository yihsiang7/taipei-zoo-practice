package com.yihsiang.zoo.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

sealed class AnimalItem

data class VenueDescriptionHeader(val venue: Venue) : AnimalItem()

object AnimalHeader : AnimalItem()

@Parcelize
data class Animal(
    @SerializedName("_id") val id: Int,
    @SerializedName("A_Name_Ch") val name: String,
    @SerializedName("A_Name_En") val enName: String,
    @SerializedName("A_Location") val location: String,
    @SerializedName("A_AlsoKnown") val alsoKnown: String,
    @SerializedName("A_Pic01_URL") val thumbnail: String,
    @SerializedName("A_Distribution") val description: String,
    @SerializedName("A_Feature") val feature: String,
    @SerializedName("A_Behavior") val behavior: String
) : AnimalItem(), Parcelable