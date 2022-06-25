package com.yihsiang.zoo.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.yihsiang.zoo.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalAnimalJsonDataSource(private val context: Context) {

    suspend fun getAnimals(): List<Animal> =
        withContext(Dispatchers.IO) {
            val jsonReader = JsonReader(
                context.resources.openRawResource(R.raw.animals).reader())

            Gson().fromJson<CommonResponse<Animal>>(
                jsonReader,
                object : TypeToken<CommonResponse<Animal>>(){}.type)
                .data
        }
}