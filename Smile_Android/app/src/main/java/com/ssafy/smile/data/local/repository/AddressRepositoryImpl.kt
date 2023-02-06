package com.ssafy.smile.data.local.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.local.database.entity.AddressEntity
import com.ssafy.smile.data.local.datasource.AddressLocalDataSource
import com.ssafy.smile.domain.model.AddressDomainDto
import com.ssafy.smile.domain.repository.AddressRepository
import com.ssafy.smile.presentation.base.BaseRepository

private const val TAG = "AddressRepositoryImpl_스마일"
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

    override suspend fun deleteAllAddress(): Int {
        return addressLocalDataSource.deleteAllAddress()
    }

    override fun getAddressList(): LiveData<List<AddressDomainDto>> {
        return addressLocalDataSource.getAddressList().map {  list ->
            list.map { entity -> entity.makeToAddressDomainDto() }
        }
    }

    override fun getAddressListWithSelection(): LiveData<List<AddressDomainDto>> {
        return addressLocalDataSource.getAddressListWithSelection().map { list ->
            list.map { entity -> entity.makeToAddressDomainDto() }
        }
    }

}
