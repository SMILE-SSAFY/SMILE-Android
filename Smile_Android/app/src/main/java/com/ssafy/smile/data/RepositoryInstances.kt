package com.ssafy.smile.data

import com.ssafy.smile.data.remote.repository.ExampleRepositoryImpl
import com.ssafy.smile.data.remote.repository.PhotographerRepositoryImpl
import com.ssafy.smile.data.remote.repository.UserRepositoryImpl
import com.ssafy.smile.domain.repository.PhotographerRepository
import javax.inject.Singleton

@Singleton
class RepositoryInstances(dataSourceInstances: DataSourceInstances) {
    @Singleton
    private val exampleRemoteRepository : ExampleRepositoryImpl = ExampleRepositoryImpl(dataSourceInstances.getExampleRemoteDataSource())

    @Singleton
    private val userRepository: UserRepositoryImpl = UserRepositoryImpl(dataSourceInstances.getUserRemoteDataSource())

    @Singleton
    private val photographerRepository : PhotographerRepositoryImpl = PhotographerRepositoryImpl(dataSourceInstances.getPhotographerRemoteDataSource())

    fun getExampleRemoteRepository() : ExampleRepositoryImpl = exampleRemoteRepository
    fun getUserRepository(): UserRepositoryImpl = userRepository
    fun getPhotographerRepository() : PhotographerRepositoryImpl = photographerRepository
}
