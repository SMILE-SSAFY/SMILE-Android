package com.ssafy.smile.presentation.view.home

import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.smile.R
import com.ssafy.smile.common.util.hideKeyboard
import com.ssafy.smile.databinding.FragmentSearchBinding
import com.ssafy.smile.presentation.adapter.SearchViewPagerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.home.SearchViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search) {
    private val searchViewModel by activityViewModels<SearchViewModel>()

    override fun initView() {
        setViewPager()
        initToolbar()
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("검색", true)
    }

    override fun setEvent() {
        setSearchEvent()
    }

    private fun setSearchEvent() {
        binding.apply {
            etSearch.setOnKeyListener { view, keyCode, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                    hideKeyboard(view)
                    searchViewModel.searchCategory = etSearch.text.toString()
                    searchViewModel.searchPhotographer(etSearch.text.toString())
                    searchViewModel.searchPost(etSearch.text.toString())
                    true
                } else {
                    false
                }
            }
        }
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