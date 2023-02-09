package com.ssafy.smile.data.local.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.local.database.dao.AddressDao
import com.ssafy.smile.data.local.database.entity.AddressEntity
import kotlinx.coroutines.flow.Flow

class AddressLocalDataSourceImpl(private val addressDao: AddressDao) : AddressLocalDataSource {
    override suspend fun insertAddress(address: AddressEntity) : Long {
        return addressDao.insertAddress(address)
    }

    override suspend fun selectAddress(address: AddressEntity): Long {
        addressDao.getAddressIsSelected()?.let {
            addressDao.updateAddressSelected(isSelected = false, address = it.address)
        }
        return addressDao.insertAddress(address)
    }

    override suspend fun deleteAddress(address: AddressEntity) : Int {
        return addressDao.deleteAddress( address = address)
    }

    override suspend fun deleteAllAddress(): Int {
        return addressDao.deleteAllAddress()
    }

    override suspend fun getSelectedAddress(): AddressEntity? {
        return addressDao.getAddressIsSelected()
    }


    override suspend fun getAddressList(): List<AddressEntity> {
        return addressDao.getAddressList()
    }

    override suspend fun getAddressListWithSelection(): List<AddressEntity> {
        return addressDao.getAddressListWithSelected()
    }

}