package com.ssafy.smile.common.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.naver.maps.geometry.LatLng
import com.ssafy.smile.domain.model.AddressGeoDomainDto
import com.ssafy.smile.domain.model.Types.GeoAddress
import java.io.IOException
import java.util.*

object AddressUtils {

    fun getRepresentAddress(address:String) : String {
        return address.split(" ")[1]
    }

    fun getGeoFromPoints(context:Context, lat:Double, lng:Double) : AddressGeoDomainDto {
        val addressList: List<Address>?
        try {
            val geocoder =  Geocoder(context, Locale.KOREA)
            addressList = geocoder.getFromLocation(lat, lng,1)
        } catch (ioException: IOException) {
            return AddressGeoDomainDto(false, GeoAddress.NETWORK_ERROR)
        } catch (illegalArgumentException: IllegalArgumentException) {
            return AddressGeoDomainDto(false, GeoAddress.GPS_ERROR)
        }
        return if (addressList == null || addressList.isEmpty()) AddressGeoDomainDto(false, GeoAddress.ENCODING_ERROR)
        else{
            val address = addressList[0].getAddressLine(0)
            if (address.contains("대한민국")) AddressGeoDomainDto(true, GeoAddress.ADDRESS, address.split("대한민국 ")[1])
            else AddressGeoDomainDto(true, GeoAddress.ADDRESS, addressList[0].getAddressLine(0).toString())
        }
    }

    fun getPointsFromGeo(context:Context, address: String) : LatLng? {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val location = geocoder.getFromLocationName(address, 1)
            if (address.isNotEmpty()) LatLng(location!![0].latitude, location[0].longitude)
            else null
        }catch (e : Exception){ null }
    }
}