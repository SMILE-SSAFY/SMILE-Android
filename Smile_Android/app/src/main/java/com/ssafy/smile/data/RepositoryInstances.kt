package com.ssafy.smile.data

import com.ssafy.smile.data.remote.repository.ExampleRemoteRepositoryImpl
import javax.inject.Singleton

@Singleton
class RepositoryInstances(dataSourceInstances: DataSourceInstances) {
    @Singleton
    private val exampleRemoteRepository : ExampleRemoteRepositoryImpl = ExampleRemoteRepositoryImpl(dataSourceInstances.getExampleRemoteDataSource())

    fun getExampleRemoteRepository() : ExampleRemoteRepositoryImpl = exampleRemoteRepository
}
