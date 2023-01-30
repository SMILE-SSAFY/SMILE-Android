package com.ssafy.smile.common.util

import android.Manifest
import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AlertDialog
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.naver.maps.geometry.LatLng
import com.ssafy.smile.domain.model.AddressGeo
import com.ssafy.smile.domain.model.Types.GeoAddress
import java.io.IOException
import java.util.*


// TODO : 스파게티 코드 리팩토링
object AddressUtils {

    fun getSelectedAddress(address:String) : String{
        return if ("(" in address) address.split(" (")[0]
        else address
    }

    fun getRepresentAddress(address:String) : String {
        return address.split(" ")[1]
    }

    fun getLatLngFromPoints(lat:Double, lng:Double) = LatLng(lat, lng)

    fun getPointsFromLatLng(latLng: LatLng) : Pair<Double, Double> =  latLng.latitude to latLng.longitude

    fun getGeoFromPoints(context:Context, lat:Double, lng:Double) : AddressGeo {
        val addressList: List<Address>?
        try {
            val geocoder =  Geocoder(context, Locale.KOREA)
            addressList = geocoder.getFromLocation(lat, lng,1)
        } catch (ioException: IOException) {
            return AddressGeo(false, GeoAddress.NETWORK_ERROR)
        } catch (illegalArgumentException: IllegalArgumentException) {
            return AddressGeo(false, GeoAddress.GPS_ERROR)
        }
        return if (addressList == null || addressList.isEmpty()) AddressGeo(false, GeoAddress.ENCODING_ERROR)
        else{
            val address = addressList[0].getAddressLine(0)
            if (address.contains("대한민국")) AddressGeo(true, GeoAddress.ADDRESS, address.split("대한민국 ")[1])
            else AddressGeo(true, GeoAddress.ADDRESS, addressList[0].getAddressLine(0).toString())
        }
    }

    fun getPointsFromGeo(context:Context, address: String) : LatLng? {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val location = geocoder.getFromLocationName(address, 1)
            if (address.isNotEmpty()) LatLng(location[0].latitude, location[0].longitude)
            else null
        }catch (e : Exception){ null }
    }
}