package com.ssafy.smile.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ssafy.smile.presentation.view.portfolio.PostViewPagerFragment
import com.ssafy.smile.presentation.view.portfolio.ReviewViewPagerFragment

class PortfolioViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity){
    private val fragmentList = listOf(PostViewPagerFragment(), ReviewViewPagerFragment())

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}