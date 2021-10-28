package com.exercise.travelbank_.data

import com.exercise.travelbank_.data.datasources.LocalDataSource
import com.exercise.travelbank_.data.datasources.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
open class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource?,
    localDataSource: LocalDataSource?
) {
    val remoteData = remoteDataSource
    val localData = localDataSource
}