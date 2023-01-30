package com.ssafy.smile.data.local.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.ssafy.smile.data.local.datasource.AddressLocalDataSource
import com.ssafy.smile.domain.model.AddressDomainDto
import com.ssafy.smile.domain.repository.AddressRepository
import com.ssafy.smile.presentation.base.BaseRepository

class AddressRepositoryImpl(private val addressLocalDataSource: AddressLocalDataSource): BaseRepository(), AddressRepository {

    override suspend fun insertAddress(address: AddressDomainDto) : Long {
        return addressLocalDataSource.insertAddress(address.makeToAddressEntity())
    }

    override suspend fun selectAddress(address: AddressDomainDto) : Long {
        return addressLocalDataSource.selectAddress(address.makeToAddressEntity())
    }

    override suspend fun deleteAddress(address: AddressDomainDto) : Int {
        return addressLocalDataSource.deleteAddress(address.makeToAddressEntity())
    }

    override fun getAddressList(): LiveData<List<AddressDomainDto>> {
        return addressLocalDataSource.getAddressList().map { list ->
            list.map { entity -> entity.makeToAddressDomainDto() }.also {
                it.sortedBy {dto -> dto.isSelected }
            }
        }
    }

}
