package com.ssafy.smile.presentation.view.portfolio

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.PostListResponseDto
import com.ssafy.smile.databinding.FragmentPostViewPagerBinding
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.PortfolioRecyclerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.home.SearchViewModel
import com.ssafy.smile.presentation.viewmodel.portfolio.PortfolioViewModel

private const val TAG = "PostViewPagerFragment_스마일"
class PostViewPagerFragment : BaseFragment<FragmentPostViewPagerBinding>(FragmentPostViewPagerBinding::bind, R.layout.fragment_post_view_pager) {
    private val portfolioViewModel: PortfolioViewModel by viewModels()

    private lateinit var portfolioRecyclerAdapter: PortfolioRecyclerAdapter
    private val recyclerData = mutableListOf<PostListResponseDto>()

    override fun initView() {
        setObserver()
        initRecycler()
    }

    private fun setObserver() {
        articlesResponseObserver()
    }

    override fun setEvent() {
    }

    private fun articlesResponseObserver() {
        portfolioViewModel.getPostsResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()

                    if (it.data.size == 0) {
                        recyclerData.clear()
                        portfolioRecyclerAdapter.notifyDataSetChanged()
                        setIsEmptyView(View.VISIBLE, View.GONE, "아직 등록한 게시물이 없습니다")
                    } else {
                        recyclerData.clear()
                        it.data.forEach { post ->
                            recyclerData.add(post)
                        }
                        portfolioRecyclerAdapter.notifyDataSetChanged()
                        setIsEmptyView(View.GONE, View.VISIBLE, null)
                    }
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    Log.d(TAG, "portfolioResponseObserver: ${it.errorCode}")
                    showToast(requireContext(), "작가 포트폴리오 조회 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
            }
        }
    }

    private fun setIsEmptyView(emptyView: Int, recyclerView: Int, emptyViewText: String?) {
        binding.apply {
            layoutEmptyView.layoutEmptyView.visibility = emptyView
            layoutEmptyView.tvEmptyView.text = emptyViewText
            recycler.visibility = recyclerView
        }
    }

    private fun initRecycler() {
        portfolioRecyclerAdapter = PortfolioRecyclerAdapter(requireContext(), recyclerData).apply {
            setItemClickListener(object : PortfolioRecyclerAdapter.OnItemClickListener {
                override fun onClick(view: View, position: Int) {
                    val action = PortfolioFragmentDirections.actionPortfolioFragmentToPostDetailFragment(recyclerData[position].id)
                    findNavController().navigate(action)
                }
            })
        }

        binding.recycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = portfolioRecyclerAdapter
        }
    }
}