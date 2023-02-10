package com.ssafy.smile.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ssafy.smile.presentation.view.home.HomeFragment
import com.ssafy.smile.presentation.view.map.MapFragment
import com.ssafy.smile.presentation.view.mypage.MyPageFragment


class MainViewPagerAdapter(activity: Fragment) : FragmentStateAdapter(activity){
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MapFragment()
            1 -> HomeFragment()
            2 -> MyPageFragment()
            else -> error("no such position: $position")
        }
    }
}