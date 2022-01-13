package com.android.recipeapp.data


import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource) {

    val remoteSource=remoteDataSource
    val localSource=localDataSource


}