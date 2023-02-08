package com.ssafy.smile.domain.repository

import androidx.lifecycle.LiveData
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.domain.model.AddressDomainDto


interface AddressRepository {
    suspend fun insertAddress(address: AddressDomainDto) : Long
    suspend fun selectAddress(address: AddressDomainDto) : Long
    suspend fun deleteAddress(address: AddressDomainDto) : Int
    suspend fun deleteAllAddress(): Int
    suspend fun getSelectedAddress() : AddressDomainDto?
    suspend fun getAddressList() : List<AddressDomainDto>
    suspend fun getAddressListWithSelection() : List<AddressDomainDto>
}