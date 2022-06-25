package com.yihsiang.zoo.di

import android.content.Context
import com.yihsiang.zoo.BuildConfig
import com.yihsiang.zoo.model.LocalAnimalJsonDataSource
import com.yihsiang.zoo.model.ZooRepository
import com.yihsiang.zoo.model.ZooService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideZooRepository(
        zooService: ZooService,
        animalJsonDataSource: LocalAnimalJsonDataSource
    ): ZooRepository = ZooRepository(zooService, animalJsonDataSource)

    @Singleton
    @Provides
    fun provideAnimalJsonDataSource(
        @ApplicationContext context: Context
    ): LocalAnimalJsonDataSource = LocalAnimalJsonDataSource(context)

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                }
            }
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://data.taipei/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideZooService(retrofit: Retrofit): ZooService =
        retrofit.create(ZooService::class.java)
}