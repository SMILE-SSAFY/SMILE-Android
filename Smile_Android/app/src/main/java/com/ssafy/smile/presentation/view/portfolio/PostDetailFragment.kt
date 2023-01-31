package com.ssafy.smile.presentation.view.portfolio

import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.smile.R
import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.databinding.FragmentPostDetailBinding
import com.ssafy.smile.domain.model.PostDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.PostViewPagerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.portfolio.PostViewModel

private const val TAG = "PostDetailFragment_싸피"
class PostDetailFragment : BaseFragment<FragmentPostDetailBinding>(FragmentPostDetailBinding::bind, R.layout.fragment_post_detail) {

    override fun initView() {

    }

    override fun setEvent() {

    }

}