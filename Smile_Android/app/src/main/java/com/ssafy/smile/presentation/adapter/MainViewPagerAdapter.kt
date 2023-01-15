package com.ssafy.smile.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ssafy.smile.presentation.view.splash.SplashFragment

class MainViewPagerAdapter (activity: FragmentActivity) : FragmentStateAdapter(activity){

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SplashFragment()
            1 -> SplashFragment()
            2 -> SplashFragment()
            3 -> SplashFragment()
            4 -> SplashFragment()
            else -> error("no such position: $position")
        }
    }
} 