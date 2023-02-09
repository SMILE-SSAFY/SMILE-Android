package com.ssafy.smile.presentation.view.mypage.myInterest

import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentMyInterestBinding
import com.ssafy.smile.presentation.adapter.MyInterestViewPagerAdapter
import com.ssafy.smile.presentation.base.BaseFragment

class MyInterestFragment : BaseFragment<FragmentMyInterestBinding>(FragmentMyInterestBinding::bind, R.layout.fragment_my_interest) {

    override fun initView() {
        initToolbar()
        initViewPager()
    }

    override fun setEvent() { }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("나의 관심", true) { moveToPopUpSelf() }
    }

    private fun initViewPager() {
        val tabTitle = listOf("작가", "게시물")
        binding.viewPager.adapter = MyInterestViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    private fun moveToPopUpSelf(){
        findNavController().navigate(R.id.action_myInterestFragment_pop)
    }
}