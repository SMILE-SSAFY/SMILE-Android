package com.ssafy.smile.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ssafy.smile.presentation.view.home.ResultPostFragment
import com.ssafy.smile.presentation.view.home.ResultPhotographerFragment

class SearchViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity){
    private val fragmentList = listOf(ResultPhotographerFragment(), ResultPostFragment())

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}