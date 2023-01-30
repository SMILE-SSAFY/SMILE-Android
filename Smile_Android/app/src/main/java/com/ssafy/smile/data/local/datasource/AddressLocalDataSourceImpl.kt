package com.ssafy.smile.data.local.datasource

import androidx.lifecycle.LiveData
import com.ssafy.smile.data.local.database.dao.AddressDao
import com.ssafy.smile.data.local.database.entity.AddressEntity
import kotlinx.coroutines.flow.Flow

class AddressLocalDataSourceImpl(private val addressDao: AddressDao) : AddressLocalDataSource {
    override suspend fun insertAddress(address: AddressEntity) : Long {
        return addressDao.insertAddress(address)
    }

    override suspend fun deleteAddress(address: AddressEntity) : Int {
        return addressDao.deleteAddress( address = address)
    }

    override suspend fun selectAddress(address: AddressEntity): Int {
        addressDao.getAddressIsSelected()?.let {
            addressDao.updateAddressSelected(isSelected = false, address = it.address)
        }
        return addressDao.updateAddressSelected(isSelected = true, address = address.address)
    }

    override fun getAddressList(): LiveData<List<AddressEntity>> {
        return addressDao.getAddressList()
    }

}