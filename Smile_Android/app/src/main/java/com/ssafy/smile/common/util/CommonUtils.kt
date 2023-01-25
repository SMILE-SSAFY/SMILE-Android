package com.ssafy.smile.common.util

import android.content.Context
import android.location.Geocoder
import com.naver.maps.geometry.LatLng
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object CommonUtils {
    //천단위 콤마
    fun makeComma(num: Int): String {
        val comma = DecimalFormat("#,###")
        return comma.format(num)
    }

    fun getAddress(context: Context, latitude: Float, longitude: Float): String {
        val geoCoder = Geocoder(context, Locale.KOREA)
        val position = LatLng(latitude.toDouble(), longitude.toDouble())
        var addr = "주소 오류"

        try {
            val loc = geoCoder.getFromLocation(position.latitude, position.longitude, 1).first()
            addr = "${loc.adminArea} ${loc.locality}"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return addr
    }

    fun dateToString(date: Date): String{
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }
}