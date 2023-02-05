package com.ssafy.smile.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ssafy.smile.domain.model.AddressDomainDto

@Entity(tableName = "address")
data class AddressEntity(
    @PrimaryKey
    val address : String,
    @ColumnInfo(name = "lat")
    val latitude : Double,
    @ColumnInfo(name = "lng")
    val longitude : Double,
    @ColumnInfo(name = "time")
    val dateTime : Long,
    @ColumnInfo(name = "selected")
    val isSelected : Boolean
){
    fun makeToAddressDomainDto(): AddressDomainDto {
        return AddressDomainDto(address, latitude, longitude, dateTime, isSelected)
    }
}