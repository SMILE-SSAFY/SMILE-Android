package com.ssafy.smile.presentation.view.portfolio

import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.smile.R
import com.ssafy.smile.common.util.Constants
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.databinding.FragmentPortfolioBinding
import com.ssafy.smile.domain.model.PortfolioDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.PortfolioViewPagerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.portfolio.PortfolioViewModel

private const val TAG = "PortfolioFragment_스마일"
class PortfolioFragment() : BaseFragment<FragmentPortfolioBinding>(FragmentPortfolioBinding::bind, R.layout.fragment_portfolio) {

    private val portfolioViewModel: PortfolioViewModel by navGraphViewModels(R.id.portfolioGraph)
    private val args: PortfolioFragmentArgs by navArgs()
    var photographerId = -1L

    override fun initView() {
        initToolbar()
        checkNavArgs()
        setPhotographerId()
        initViewPager()
        portfolioViewModel.getPortfolio(photographerId)
        portfolioViewModel.getPosts(photographerId)
        setObserver()
    }

    private fun checkNavArgs(){
        if (args.photographerId<0 && args.postId<0) {
            showToast(requireContext(), requireContext().getString(R.string.msg_common_error, "정보를 불러오는"), Types.ToastType.ERROR)
            moveToPopUpSelf()
        }
        else if (args.postId>0) {
            val action = PortfolioFragmentDirections.actionPortfolioFragmentToPostDetailFragmentWithPop(args.isFromMap, args.postId)
            findNavController().navigate(action)
        }
        else setPhotographerId()
    }

    private fun setPhotographerId() {
        portfolioViewModel.photographerId = args.photographerId
        photographerId = portfolioViewModel.photographerId
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("프로필", true) { moveToPopUpSelf() }
    }

    private fun moveToPopUpSelf() {
        findNavController().navigate(R.id.action_portfolioFragment_pop)
    }

    private fun setObserver() {
        portfolioResponseObserver()
        photographerHeartResponseObserver()
    }

    override fun setEvent() {
        setRefreshLayoutEvent()
        binding.apply {
            ctvLike.setOnClickListener {
                portfolioViewModel.photographerHeart(photographerId)
            }
            btnReservation.setOnClickListener {
                val action = PortfolioFragmentDirections.actionPortfolioFragmentToReservationFragment(photographerId)
                findNavController().navigate(action)
            }
            btnWritePost.setOnClickListener {
                findNavController().navigate(R.id.action_portfolioFragment_to_writePostFragment)
            }
        }
    }

    private fun setRefreshLayoutEvent() {
        binding.apply {
            refreshLayout.setOnRefreshListener {
                portfolioViewModel.getPortfolio(photographerId)
                portfolioViewModel.getPosts(photographerId)
                portfolioViewModel.getPhotographerReviewList(photographerId)
                refreshLayout.isRefreshing = false
            }
        }
    }

    private fun initViewPager() {
        val viewPagerAdapter = PortfolioViewPagerAdapter(this)
        val tabTitle = listOf("게시물", "작가 리뷰")

        binding.viewPager.apply {
            adapter = viewPagerAdapter
            offscreenPageLimit = 2
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    private fun portfolioResponseObserver() {
        portfolioViewModel.getPortfolioResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()
                    setPhotographerInfo(it.data.toPortfolioDomainDto())
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    if (args.postId<0){
                        showToast(requireContext(), "작가 포트폴리오 조회 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.ERROR)
                    }
                }
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
            }
        }
    }

    private fun photographerHeartResponseObserver() {
        binding.apply {
            // TODO : like누른 후 reponse livedata 호출 안됨.
            portfolioViewModel.photographerHeartResponse.observe(viewLifecycleOwner) {
                Log.d(TAG, "photographerHeartResponseObserver: observer 실행")
                when(it) {
                    is NetworkUtils.NetworkResponse.Loading -> { }
                    is NetworkUtils.NetworkResponse.Success -> {
                        Log.d(TAG, "photographerHeartResponseObserver: Success")
                        ctvLike.toggle()
                        portfolioViewModel.getPortfolio(photographerId)
                    }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        showToast(requireContext(), "작가 좋아요 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.ERROR)
                    }
                }
            }
        }
    }

    private fun setPhotographerInfo(portfolioDomainDto: PortfolioDomainDto) {
        binding.apply {
            setButtons(portfolioDomainDto.isMe)
            Glide.with(requireContext())
                .load(Constants.IMAGE_BASE_URL+portfolioDomainDto.profileImg)
                .into(ivProfile)
            tvCategory.text = portfolioDomainDto.category
            tvName.text = portfolioDomainDto.photographerName
            tvPlace.text = portfolioDomainDto.place
            tvIntroduction.text = portfolioDomainDto.introduction
            ctvLike.isChecked = portfolioDomainDto.isHeart
            tvLike.text = portfolioDomainDto.hearts.toString()
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