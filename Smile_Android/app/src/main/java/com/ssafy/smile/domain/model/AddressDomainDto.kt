package com.ssafy.smile.domain.model

import android.os.Parcelable
import com.ssafy.smile.data.local.database.entity.AddressEntity
import kotlinx.parcelize.Parcelize


@Parcelize
data class AddressDomainDto(
    var address : String = "",
    var latitude : Double = 0.0,
    var longitude : Double = 0.0,
    var dateTime : Long = System.currentTimeMillis(),
    var isSelected: Boolean = false
) : Parcelable {
    fun makeToAddressEntity() = AddressEntity(address, latitude, longitude, dateTime, isSelected)
}
