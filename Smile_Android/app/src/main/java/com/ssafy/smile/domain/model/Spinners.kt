package com.ssafy.smile.domain.model

import android.content.Context
import android.widget.ArrayAdapter
import com.ssafy.smile.R
import com.ssafy.smile.domain.model.Types.Region

object Spinners {
    fun getSelectedArrayAdapter(context: Context, resource:Int) : ArrayAdapter<String> {
        val array = context.resources.getStringArray(resource)
        return ArrayAdapter(context, R.layout.item_spinner, array)
    }
    fun getSelectedPlaceArrayResource(region : String) : Int {
        when (region){
            Region.SEOUL.getValue() -> return R.array.spinner_region_seoul
            Region.INCHEON.getValue() -> return R.array.spinner_region_incheon
            Region.PUSAN.getValue() -> return R.array.spinner_region_pusan
            Region.DAEGU.getValue() -> return R.array.spinner_region_daegu
            Region.DAEJEON.getValue()-> return R.array.spinner_region_daejeon
            Region.ULSAN.getValue() -> return R.array.spinner_region_ulsan
            Region.GUANGJU.getValue() -> return R.array.spinner_region_gwangju
            Region.SAEJONG.getValue() -> return R.array.spinner_region_sejong
            Region.GEUNGGI.getValue() -> return R.array.spinner_region_gyeonggi
            Region.GANGWON.getValue() -> return R.array.spinner_region_gangwon
            Region.GEONGSANGNAMDO.getValue() -> return R.array.spinner_region_gyeong_nam
            Region.GEONGSANGBUKDO.getValue() -> return R.array.spinner_region_gyeong_buk
            Region.JEONRANAMDO.getValue() -> return R.array.spinner_region_jeon_nam
            Region.JEONRABUKDO.getValue() -> return R.array.spinner_region_jeon_buk
            Region.CHOONCHUNGNAMDO.getValue() -> return R.array.spinner_region_chung_nam
            Region.CHOONCHUNGBUKDO.getValue() -> return R.array.spinner_region_chung_buk
            Region.JEJUDO.getValue() -> return R.array.spinner_region_jeju
            else -> return R.array.spinner_region_seoul
        }
    }
}
