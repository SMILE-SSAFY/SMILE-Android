package com.ssafy.smile.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.local.database.entity.AddressEntity
import retrofit2.http.PUT

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(address: AddressEntity) : Long

    @Delete
    suspend fun deleteAddress(address: AddressEntity) : Int

    @Query("DELETE FROM address")
    suspend fun deleteAllAddress(): Int

    @Query("UPDATE address SET selected = :isSelected WHERE address = :address")
    fun updateAddressSelected(isSelected: Boolean, address: String) : Int

    @Query("SELECT * FROM address WHERE selected = :isSelected")
    fun getAddressIsSelected(isSelected: Boolean = true) : AddressEntity?

    @Query("SELECT * FROM address ORDER by time DESC")
    fun getAddressList() : LiveData<List<AddressEntity>>

    @Query("SELECT * FROM address ORDER by selected DESC, time DESC")
    fun getAddressListWithSelected() : LiveData<List<AddressEntity>>
}