package com.ssafy.smile.presentation.view.portfolio

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.smile.MainActivity
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.databinding.FragmentPortfolioBinding
import com.ssafy.smile.domain.model.PortfolioDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.PortfolioViewPagerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.LikeViewModel
import com.ssafy.smile.presentation.viewmodel.PortfolioViewModel

private const val TAG = "PortfolioFragment_스마일"
class PortfolioFragment() : BaseFragment<FragmentPortfolioBinding>(FragmentPortfolioBinding::bind, R.layout.fragment_portfolio) {

    private val portfolioViewModel by activityViewModels<PortfolioViewModel>()
    private val likeViewModel by activityViewModels<LikeViewModel>()
    private var photographerId: Long = -1

    override fun initView() {
        (activity as MainActivity).setToolBar(true, isBackUsed = true, title = "프로필")
        setViewPager()
        setPhotographerId()
        portfolioViewModel.getPortfolio(photographerId)
        portfolioResponseObserver()
        photographerLikeResponseObserver()
        photographerLikeCancelResponseObserver()
    }

    override fun setEvent() {
        binding.apply {
            ctvLike.setOnClickListener {
                if (ctvLike.isChecked) {
                    likeViewModel.photographerLikeCancel(photographerId)
                } else {
                    likeViewModel.photographerLike(photographerId)
                }
            }
        }
    }

    private fun setViewPager() {
        val viewPagerAdapter = PortfolioViewPagerAdapter(requireActivity())
        val tabTitle = listOf("게시물", "작가 리뷰")

        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    private fun setPhotographerId() {
        // TODO : 이전 화면에서 작가 id 넘겨준 값 가져와서 photographerId 변수 값 변경하기
    }

    private fun portfolioResponseObserver() {
        portfolioViewModel.getPortfolioResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Success -> {
                    setPhotographerInfo(it.data.toPortfolioDomainDto())
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    Log.d(TAG, "portfolioResponseObserver: ${it.errorCode}")
                    showToast(requireContext(), "작가 포트폴리오 조회 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
                is NetworkUtils.NetworkResponse.Loading -> {}
            }
        }
    }

    private fun photographerLikeResponseObserver() {
        binding.apply {
            likeViewModel.photographerLikeResponse.observe(viewLifecycleOwner) {
                when(it) {
                    is NetworkUtils.NetworkResponse.Success -> {
                        ctvLike.toggle()
                    }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        // TODO: error code 물어보기
                        if (it.errorCode == 400) {
                            showToast(requireContext(), "이미 좋아요를 누른 작가입니다.")
                        } else {
                            showToast(requireContext(), "작가 좋아요 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                        }
                    }
                    is NetworkUtils.NetworkResponse.Loading -> {}
                }
            }
        }
    }

    private fun photographerLikeCancelResponseObserver() {
        binding.apply {
            likeViewModel.photographerLikeResponse.observe(viewLifecycleOwner) {
                when(it) {
                    is NetworkUtils.NetworkResponse.Success -> {
                        ctvLike.toggle()
                    }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        // TODO: error code 물어보기
                        if (it.errorCode == 404) {
                            showToast(requireContext(), "존재하지 않는 작가입니다.")
                        } else {
                            showToast(requireContext(), "작가 좋아요 취소 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                        }
                    }
                    is NetworkUtils.NetworkResponse.Loading -> {}
                }
            }
        }
    }

    private fun setPhotographerInfo(portfolioDomainDto: PortfolioDomainDto) {
        binding.apply {
            setButtons(portfolioDomainDto.isMe)
            tvCategory.text = portfolioDomainDto.categoryName
            tvName.text = portfolioDomainDto.photographerName
            tvPlace.text = portfolioDomainDto.place
            tvPrice.text = portfolioDomainDto.categoryPrice
            tvIntroduction.text = portfolioDomainDto.introduction
            ctvLike.isChecked = portfolioDomainDto.isLike
        }
    }

    private fun setButtons(isMe: Boolean) {
        binding.apply {
            if (isMe) {
                btnWritePost.visibility = View.VISIBLE
            } else {
                btnReservation.visibility = View.VISIBLE
            }
        }
    }
}