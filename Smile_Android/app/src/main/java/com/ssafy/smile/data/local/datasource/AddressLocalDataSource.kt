package com.ssafy.smile.data.local.datasource

import androidx.lifecycle.LiveData
import com.ssafy.smile.data.local.database.dao.AddressDao
import com.ssafy.smile.data.local.database.entity.AddressEntity
import kotlinx.coroutines.flow.Flow

interface AddressLocalDataSource {
    suspend fun insertAddress(address: AddressEntity) : Long
    suspend fun selectAddress(address: AddressEntity) : Long
    suspend fun deleteAddress(address: AddressEntity) : Int
    fun getCurrentAddress() : LiveData<AddressEntity>
    fun getAddressList() : LiveData<List<AddressEntity>>
    fun getAddressListWithSelection() : LiveData<List<AddressEntity>>
}