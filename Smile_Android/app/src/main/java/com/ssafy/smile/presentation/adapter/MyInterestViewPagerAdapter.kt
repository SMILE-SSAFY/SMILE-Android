package com.ssafy.smile.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ssafy.smile.presentation.view.mypage.myInterest.MyInterestArticleFragment
import com.ssafy.smile.presentation.view.mypage.myInterest.MyInterestPhotographerFragment

class MyInterestViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity){
    private val fragmentList = listOf(MyInterestPhotographerFragment(), MyInterestArticleFragment())

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}