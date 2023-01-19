package com.ssafy.smile.presentation.view.portfolio

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentPostViewPagerBinding
import com.ssafy.smile.domain.model.Article
import com.ssafy.smile.presentation.adapter.PortfolioRecyclerAdapter
import com.ssafy.smile.presentation.base.BaseFragment

class PostViewPagerFragment : BaseFragment<FragmentPostViewPagerBinding>(FragmentPostViewPagerBinding::bind, R.layout.fragment_post_view_pager) {
    private lateinit var portfolioRecyclerAdapter: PortfolioRecyclerAdapter
    private val recyclerData = mutableListOf<Article>()

    override fun initView() {
        initRecycler()
    }

    override fun setEvent() {
        postItemClickListener()
    }

    private fun initRecycler() {
        //TODO : 서버 통신 후 지우기
        for (i in 1..10) {
            recyclerData.add(Article(1, "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.travie.com%2Fnews%2FarticleView.html%3Fidxno%3D19975&psig=AOvVaw3UTiDGB6NeFtbVr4Z_nlOn&ust=1674198269589000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCMjp3tqI0_wCFQAAAAAdAAAAABAE"))
            recyclerData.add(Article(2, "https://www.google.com/url?sa=i&url=https%3A%2F%2Fnews.samsungdisplay.com%2F15580&psig=AOvVaw3UTiDGB6NeFtbVr4Z_nlOn&ust=1674198269589000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCMjp3tqI0_wCFQAAAAAdAAAAABAI"))
            recyclerData.add(Article(3, "https://www.walkerhillstory.com/wp-content/uploads/2020/09/%EC%9D%B8%EC%83%9D%EC%82%AC%EC%A7%84-%EC%9B%90%EA%B3%A0-%EB%8C%80%ED%91%9C%EC%82%AC%EC%A7%84.jpg"))
            recyclerData.add(Article(4, "https://www.econovill.com/news/photo/201812/353683_236958_5647.jpg"))
            recyclerData.add(Article(5, "https://cdn.travie.com/news/photo/first/201611/img_19431_1.jpg"))
            recyclerData.add(Article(6, "https://blog.kakaocdn.net/dn/cxXz0g/btrpdnEFZxC/kLMtiZk07PMa0rmvLGF1g0/img.jpg"))
        }

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