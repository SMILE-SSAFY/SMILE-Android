package com.ssafy.smile.presentation.view.portfolio

import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
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

    private val postViewModel: PostViewModel by navGraphViewModels(R.id.portfolioGraph)
    private lateinit var postViewPagerAdapter: PostViewPagerAdapter
    private var imageData = mutableListOf<String>()

    private val args: PostDetailFragmentArgs by navArgs()
    var postId = -1L

    override fun initView() {
        initToolbar()
        setPostId()
        postViewModel.getPostById(postId)
        setObserver()
    }

    private fun setPostId() {
        postId = args.postId
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("게시물", true) { moveToPopUpSelf() }
    }

    private fun moveToPopUpSelf() = findNavController().navigate(R.id.action_postDetailFragment_pop)

    private fun setObserver() {
        getPostByIdResponseObserver()
        postLikeResponseObserver()
    }

    override fun setEvent() {
        binding.apply {

            ivPostMore.setOnClickListener {
                val action = PostDetailFragmentDirections.actionPostDetailFragmentToPostEditBottomSheetDialogFragment(postId)
                findNavController().navigate(action)
            }
            ctvLike.setOnClickListener {
                postViewModel.postHeart(postId)
            }
        }
    }

    private fun getPostByIdResponseObserver() {
        postViewModel.getPostByIdResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()
                    Log.d(TAG, "getPostByIdResponseObserver: ${it.data.toPostDomainDto()}")
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
            tvPlace.text = post.detailAddress
            imageData = post.photoUrl
            ctvLike.isChecked = post.isHeart
            tvLike.text = post.hearts.toString()
            tvCategory.text = post.category
            tvCreatedAt.text = post.createdAt.substring(0, 10)
        }
        initViewPager()
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
            postViewModel.postHeartResponse.observe(viewLifecycleOwner) {
                when(it) {
                    is NetworkUtils.NetworkResponse.Success -> {
                        ctvLike.toggle()
                        postViewModel.getPostById(postId)
                    }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        showToast(requireContext(), "게시물 좋아요 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                    }
                    is NetworkUtils.NetworkResponse.Loading -> {
                    }
                }
            }
        }
    }

}