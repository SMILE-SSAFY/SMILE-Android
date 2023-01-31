package com.ssafy.smile.domain.repository

import androidx.lifecycle.LiveData
import com.ssafy.smile.domain.model.AddressDomainDto
import kotlinx.coroutines.flow.Flow


interface AddressRepository {
    suspend fun insertAddress(address: AddressDomainDto) : Long
    suspend fun selectAddress(address: AddressDomainDto) : Long
    suspend fun deleteAddress(address: AddressDomainDto) : Int
    fun getAddressList() : LiveData<List<AddressDomainDto>>
}