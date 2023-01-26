package com.ssafy.smile.presentation.view.home

import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentSearchResultBinding
import com.ssafy.smile.presentation.adapter.SearchViewPagerAdapter
import com.ssafy.smile.presentation.base.BaseFragment

class SearchResultFragment : BaseFragment<FragmentSearchResultBinding>(FragmentSearchResultBinding::bind, R.layout.fragment_search_result) {
    override fun initView() {
        setViewPager()
    }

    override fun setEvent() {
//        TODO("Not yet implemented")
    }

    private fun setViewPager() {
        val viewPagerAdapter = SearchViewPagerAdapter(requireActivity())
        val tabTitle = listOf("작가", "게시물")

        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }
}