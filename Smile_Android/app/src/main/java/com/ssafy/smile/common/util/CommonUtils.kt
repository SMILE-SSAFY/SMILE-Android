package com.ssafy.smile.common.util

import android.content.Context
import android.location.Geocoder
import android.os.Parcelable
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
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

    fun getDiffDistance(meter: Double): String {
        return if (meter < 1000) meter.toInt().toString() + "m"
        else (meter / 1000).toInt().toString() + "km"
    }


    fun getDiffTime(date : Date) : String{
        val now = System.currentTimeMillis()
        val time = date.time
        val diff = now - time
        val result : String = if(diff >= 24*60*60*1000){            // 하루가 넘을 때
            SimpleDateFormat("MM월 dd일").format(time).toString()
        } else if(diff >= 60*60*1000){                              //한 시간 넘을 때
            "${diff/(60*60*1000)}시간 전"
        }else{                                                          //한 시간 전
            "${diff/(60*1000)}분 전"
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

    fun getCategoryName(category: ArrayList<String>): String {
        val categoryNames = arrayListOf<String>()
        category.forEach { data ->
            if (data !in categoryNames) {
                categoryNames.add(data)
            }
        }

        return when (categoryNames.size) {
            1 -> {
                "#${categoryNames[0]}"
            }
            2 -> {
                "#${categoryNames[0]}  #${categoryNames[1]}"
            }
            else -> {
                "#${categoryNames[0]}  #${categoryNames[1]}..."
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

    fun saveRecyclerViewState(recyclerView: RecyclerView): Parcelable? {
        return recyclerView.layoutManager!!.onSaveInstanceState()
    }

    fun setSavedRecyclerViewState(recyclerViewState: Parcelable?, recyclerView: RecyclerView) {
        recyclerView.layoutManager!!.onRestoreInstanceState(recyclerViewState)
    }
}