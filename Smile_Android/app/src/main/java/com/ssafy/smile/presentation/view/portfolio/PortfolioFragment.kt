package com.ssafy.smile.presentation.view.portfolio

import android.util.Log
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.smile.MainActivity
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentPortfolioBinding
import com.ssafy.smile.presentation.adapter.PortfolioViewPagerAdapter
import com.ssafy.smile.presentation.base.BaseFragment

private const val TAG = "PortfolioFragment_스마일"
class PortfolioFragment() : BaseFragment<FragmentPortfolioBinding>(FragmentPortfolioBinding::bind, R.layout.fragment_portfolio) {
    override fun initView() {
        (activity as MainActivity).setToolBar(true, isBackUsed = true, title = "프로필")
        Log.d(TAG, "initView: ${activity as MainActivity}")

        setViewPager()

        binding.appCompatButton3.setOnClickListener {
            (activity as MainActivity).setToolBar(true, isBackUsed = true, title = "프로필")
        }
    }

    override fun setEvent() {
    }

    private fun setViewPager() {
        val viewPagerAdapter = PortfolioViewPagerAdapter(requireActivity())
        val tabTitle = listOf("게시물", "작가 리뷰")

        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }
}