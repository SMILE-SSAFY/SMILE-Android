package com.ssafy.smile.presentation.view.mypage.myInterest

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.databinding.FragmentMyInterestBinding
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.MyInterestViewPagerAdapter
import com.ssafy.smile.presentation.adapter.PortfolioViewPagerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.mypage.MyInterestViewModel

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
        binding.viewPager.adapter = MyInterestViewPagerAdapter(requireActivity())
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    private fun moveToPopUpSelf(){
        findNavController().navigate(R.id.action_myInterestFragment_pop)
    }
}