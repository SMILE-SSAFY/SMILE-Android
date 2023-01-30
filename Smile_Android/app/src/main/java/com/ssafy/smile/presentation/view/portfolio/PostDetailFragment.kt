package com.ssafy.smile.presentation.view.portfolio

import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.smile.MainActivity
import com.ssafy.smile.R
import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.databinding.FragmentPostDetailBinding
import com.ssafy.smile.domain.model.PostDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.PostViewPagerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.LikeViewModel
import com.ssafy.smile.presentation.viewmodel.portfolio.PostViewModel

private const val TAG = "PostDetailFragment_싸피"
class PostDetailFragment : BaseFragment<FragmentPostDetailBinding>(FragmentPostDetailBinding::bind, R.layout.fragment_post_detail) {
    private val postViewModel by activityViewModels<PostViewModel>()
    private val likeViewModel by activityViewModels<LikeViewModel>()
    private lateinit var postViewPagerAdapter: PostViewPagerAdapter
    private var imageData = mutableListOf<String>()

    private val args: PortfolioFragmentArgs by navArgs()

    override fun initView() {
        initToolbar()
        setObserver()
        postViewModel.getPostById(args.postId)
        initViewPager()
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("게시물", true)
    }

    private fun setObserver() {
        getPostByIdResponseObserver()
        postLikeResponseObserver()
        postLikeCancelResponseObserver()
    }

    override fun setEvent() {
        binding.apply {
            ivPostMore.setOnClickListener {
                val action = PostDetailFragmentDirections.actionPostDetailFragmentToPostEditBottomSheetDialogFragment(args.postId)
                findNavController().navigate(action)
            }
        }
    }

    private fun getPostByIdResponseObserver() {
        postViewModel.getPostByIdResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()
                    setPostInfo(it.data.toPostDomainDto())
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    Log.d(TAG, "postByIdResponseObserver: ${it.errorCode}")
                    showToast(requireContext(), "게시글 조회 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
            }
        }
    }

    private fun setPostInfo(post: PostDomainDto) {
        binding.apply {
            if (post.isMe) {
                ivPostMore.visibility = View.VISIBLE
            }
            tvPhotographerName.text = post.photographerName
            tvPlace.text = CommonUtils.getAddress(requireContext(), post.latitude, post.longitude)
            imageData = post.photoUrl
            ctvLike.isChecked = post.isLike
            tvLike.text = post.heart
            tvCategory.text = post.category
            tvCreatedAt.text = post.createdAt
        }
    }

    private fun initViewPager() {
        postViewPagerAdapter = PostViewPagerAdapter(requireContext(), imageData)

        binding.apply {
            vpImages.adapter = postViewPagerAdapter
            tlIndicator.count = imageData.size
            vpImages.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tlIndicator.selection = position
                }
            })
        }
    }

    private fun postLikeResponseObserver() {
        binding.apply {
            likeViewModel.postLikeResponse.observe(viewLifecycleOwner) {
                when(it) {
                    is NetworkUtils.NetworkResponse.Success -> {
                        dismissLoadingDialog()
                        ctvLike.toggle()
                    }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        dismissLoadingDialog()
                        if (it.errorCode == 400) {
                            showToast(requireContext(), "이미 좋아요를 누른 게시물입니다.")
                        } else {
                            showToast(requireContext(), "게시물 좋아요 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                        }
                    }
                    is NetworkUtils.NetworkResponse.Loading -> {
                        showLoadingDialog(requireContext())
                    }
                }
            }
        }
    }

    private fun postLikeCancelResponseObserver() {
        binding.apply {
            likeViewModel.postLikeCancelResponse.observe(viewLifecycleOwner) {
                when(it) {
                    is NetworkUtils.NetworkResponse.Success -> {
                        dismissLoadingDialog()
                        ctvLike.toggle()
                    }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        dismissLoadingDialog()
                        if (it.errorCode == 404) {
                            showToast(requireContext(), "존재하지 않는 게시물입니다.")
                        } else {
                            showToast(requireContext(), "게시물 좋아요 취소 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                        }
                    }
                    is NetworkUtils.NetworkResponse.Loading -> {
                        showLoadingDialog(requireContext())
                    }
                }
            }
        }
    }
}