package com.ssafy.smile.common.util

import android.content.Context
import android.location.Geocoder
import com.naver.maps.geometry.LatLng
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object CommonUtils {

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

    fun getCategoryName(category: ArrayList<String>): String {
        return when (category.size) {
            1 -> {
                "#${category[0]}"
            }
            2 -> {
                "#${category[0]}  #${category[1]}"
            }
            else -> {
                "#${category[0]}  #${category[1]}..."
            }
        }
    }

    fun getPlace(places: ArrayList<String>): String {
        return when (places.size) {
            1 -> {
                "${places[0]}"
            }
            2 -> {
                "${places[0]}, ${places[1]}"
            }
            else -> {
                "${places[0]}, ${places[1]}, ..."
            }
        }
    }

    fun getCategoryPrice(category: ArrayList<Int>): String {
        category.sort()
        return "${makeComma(category[0])}원 부터~"
    }

    fun deleteQuote(str: String): String {
        return str.replace("\"", "")
    }

    fun combineCategoryAndOption(category: String, option: String): String {
        return "$category $option"
    }
}