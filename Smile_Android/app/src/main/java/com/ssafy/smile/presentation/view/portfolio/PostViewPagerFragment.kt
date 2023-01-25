package com.ssafy.smile.presentation.view.portfolio

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.Article
import com.ssafy.smile.databinding.FragmentPostViewPagerBinding
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.PortfolioRecyclerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.portfolio.PortfolioViewModel

private const val TAG = "PostViewPagerFragment_스마일"
class PostViewPagerFragment : BaseFragment<FragmentPostViewPagerBinding>(FragmentPostViewPagerBinding::bind, R.layout.fragment_post_view_pager) {
    private val portfolioViewModel by activityViewModels<PortfolioViewModel>()

    private lateinit var portfolioRecyclerAdapter: PortfolioRecyclerAdapter
    private val recyclerData = mutableListOf<Article>()
    private var photographerId: Long = -1

    override fun initView() {
        portfolioViewModel.getArticles(photographerId)
        articlesResponseObserver()
        initRecycler()
    }

    override fun setEvent() {
        postItemClickListener()
    }

    private fun articlesResponseObserver() {
        portfolioViewModel.getArticlesResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Success -> {
                    it.data.articles.forEach { article ->
                        recyclerData.add(article)
                    }
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    Log.d(TAG, "portfolioResponseObserver: ${it.errorCode}")
                    showToast(requireContext(), "작가 포트폴리오 조회 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
                is NetworkUtils.NetworkResponse.Loading -> {}
            }
        }
    }

    private fun initRecycler() {
        portfolioRecyclerAdapter = PortfolioRecyclerAdapter(requireContext(), recyclerData)

        binding.recycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = portfolioRecyclerAdapter
        }
    }

    private fun postItemClickListener() {
        portfolioRecyclerAdapter.onItemClickListener = object : PortfolioRecyclerAdapter.OnItemClickListener {
            override fun onClick(view: View, position: Int) {
                val action = PortfolioFragmentDirections.actionPortfolioFragmentToPostDetailFragment(recyclerData[position].articleId)
                findNavController().navigate(action)
            }
        }
    }
}