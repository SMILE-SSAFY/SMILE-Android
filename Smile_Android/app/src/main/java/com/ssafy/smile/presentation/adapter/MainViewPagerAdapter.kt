package com.ssafy.smile.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ssafy.smile.presentation.view.home.HomeFragment
import com.ssafy.smile.presentation.view.map.MapFragment
import com.ssafy.smile.presentation.view.mypage.MypageFragment
import com.ssafy.smile.presentation.view.portfolio.WritePostFragment

class MainViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity){
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MapFragment()
            1 -> HomeFragment()
            2 -> WritePostFragment()
            else -> error("no such position: $position")
        }
    }
}