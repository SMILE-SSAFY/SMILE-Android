package com.ssafy.smile.presentation.view.portfolio

import androidx.navigation.fragment.navArgs
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentPostDetailBinding
import com.ssafy.smile.presentation.base.BaseFragment

class PostDetailFragment : BaseFragment<FragmentPostDetailBinding>(FragmentPostDetailBinding::bind, R.layout.fragment_post_detail) {

    private val args: PortfolioFragmentArgs by navArgs()

    override fun initView() {
        binding.tvId.text = args.articleId.toString()
    }

    override fun setEvent() {
    }
}