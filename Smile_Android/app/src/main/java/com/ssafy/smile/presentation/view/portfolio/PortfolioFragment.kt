package com.ssafy.smile.presentation.view.portfolio

import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.databinding.FragmentPortfolioBinding
import com.ssafy.smile.domain.model.PortfolioDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.PortfolioViewPagerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.portfolio.PortfolioViewModel

private const val TAG = "PortfolioFragment_스마일"
class PortfolioFragment() : BaseFragment<FragmentPortfolioBinding>(FragmentPortfolioBinding::bind, R.layout.fragment_portfolio) {
    override fun initView() {
        // TODO("Not yet implemented")
    }

    override fun setEvent() {
        // TODO("Not yet implemented")
    }

}