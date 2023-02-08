package com.ssafy.smile.data.local.datasource

import androidx.lifecycle.LiveData
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.local.database.dao.AddressDao
import com.ssafy.smile.data.local.database.entity.AddressEntity
import kotlinx.coroutines.flow.Flow

interface AddressLocalDataSource {
    suspend fun insertAddress(address: AddressEntity) : Long
    suspend fun selectAddress(address: AddressEntity) : Long
    suspend fun deleteAddress(address: AddressEntity) : Int
    suspend fun deleteAllAddress(): Int
    suspend fun getAddressList() : List<AddressEntity>
    suspend fun getAddressListWithSelection() : List<AddressEntity>
}