package com.ssafy.smile.data

import com.ssafy.smile.data.remote.repository.ExampleRepositoryImpl
import javax.inject.Singleton

@Singleton
class RepositoryInstances(dataSourceInstances: DataSourceInstances) {
    @Singleton
    private val exampleRemoteRepository : ExampleRepositoryImpl = ExampleRepositoryImpl(dataSourceInstances.getExampleRemoteDataSource())

    fun getExampleRemoteRepository() : ExampleRepositoryImpl = exampleRemoteRepository
}
