package com.ssafy.smile.common.util

import android.content.Context
import android.location.Geocoder
import com.naver.maps.geometry.LatLng
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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

    fun getDiffTime(date : Date) : String{
        val now = System.currentTimeMillis()
        val time = date.time
        val diff = now - time
        val result : String = if(diff >= 24*60*60*1000){            // 하루가 넘을 때
            SimpleDateFormat("yyyy-MM-dd HH:mm").format(time).toString()
        } else if(diff >= 60*60*1000){                              //한 시간 넘을 때
            "${diff/(60*60*1000)}시간전"
        }else{                                                          //한 시간 전
            "${diff/(60*1000)}분전"
        }
        return result
    }

    fun stringToDate(dateStr :String) : Date? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var date : Date? = null
        try { date = dateFormat.parse(dateStr) }
        catch (e: ParseException) { e.printStackTrace() }
        return date
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
                places[0]
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