package com.ssafy.smile.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.smile.R
import com.ssafy.smile.databinding.ActivityMainBinding
import com.ssafy.smile.databinding.FragmentMainBinding
import com.ssafy.smile.presentation.adapter.MainViewPagerAdapter


class MainFragment : Fragment() {

    private lateinit var viewbinding : FragmentMainBinding
    private var mainViewPagerAdapter : MainViewPagerAdapter? = null

    private fun initViewPager(){

        viewbinding.run{

            mainViewPagerAdapter = MainViewPagerAdapter(requireActivity())
            mainhomeViewpager.apply {
                offscreenPageLimit = 5
                adapter = mainViewPagerAdapter
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        mainhomeBottomNavi.selectedItemId = when(position){
                            0 -> R.id.btn_home
                            1 -> R.id.btn_reservation
                            2 -> R.id.btn_community
                            3 -> R.id.btn_alarm
                            4 -> R.id.btn_mypage
                            else -> error("no such position: $position") } } })
                isUserInputEnabled = false
            }


            mainhomeBottomNavi.setOnNavigationItemSelectedListener { itemclicklistener ->
                when(itemclicklistener.setChecked(true).itemId){
                    R.id.btn_home -> {
                        mainhomeViewpager.setCurrentItem(0, false)
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.btn_reservation -> {
                        mainhomeViewpager.setCurrentItem(1, false)
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.btn_community -> {
                        mainhomeViewpager.setCurrentItem(2, false)
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.btn_alarm -> {
                        mainhomeViewpager.setCurrentItem(3, false)
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.btn_mypage -> {
                        mainhomeViewpager.setCurrentItem(4, false)
                        return@setOnNavigationItemSelectedListener true }
                    else -> error("no such position!")
                }
                return@setOnNavigationItemSelectedListener false
            }
        }
    }
}