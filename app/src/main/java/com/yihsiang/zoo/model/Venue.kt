package com.yihsiang.zoo.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Venue(
    @SerializedName("_id") val id: Int,
    @SerializedName("e_name") val name: String,
    @SerializedName("e_info") val description: String,
    @SerializedName("e_memo") val businessHours: String,
    @SerializedName("e_pic_url") val thumbnail: String,
    @SerializedName("e_category") val category: String
) : Parcelable {
    val hasBusinessHours: Boolean
        get() = businessHours.isNotEmpty()
}
