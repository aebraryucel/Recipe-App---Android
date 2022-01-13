package com.android.recipeapp.di

import com.android.recipeapp.data.RecipesApi
import com.android.recipeapp.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideConverterFactory():GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient():OkHttpClient{
        return OkHttpClient.Builder()
            .readTimeout(10,TimeUnit.SECONDS)
            .connectTimeout(10,TimeUnit.SECONDS)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient,gsonConverterFactory: GsonConverterFactory): RecipesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(RecipesApi::class.java)
    }
}