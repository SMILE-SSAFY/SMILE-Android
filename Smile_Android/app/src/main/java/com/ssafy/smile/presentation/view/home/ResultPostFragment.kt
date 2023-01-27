package com.ssafy.smile.presentation.view.home

import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentResultPostBinding
import com.ssafy.smile.domain.model.CustomPostDomainDto
import com.ssafy.smile.presentation.adapter.ResultPostRecyclerAdapter
import com.ssafy.smile.presentation.base.BaseFragment

class ResultPostFragment : BaseFragment<FragmentResultPostBinding>(FragmentResultPostBinding::bind, R.layout.fragment_result_post) {
    private lateinit var resultPostRecyclerAdapter: ResultPostRecyclerAdapter
    private val recyclerData = mutableListOf<CustomPostDomainDto>()

    override fun initView() {
        for (i in 1..10) {
            recyclerData.add(
                CustomPostDomainDto(
                    "https://edu.ssafy.com/asset/images/header-logo.jpg",
                    "싸피",
                    "싸피",
                    "싸피",
                    false,
                    100
                )
            )
            recyclerData.add(
                CustomPostDomainDto(
                    "https://edu.ssafy.com/asset/images/header-logo.jpg",
                    "싸피",
                    "싸피",
                    "싸피",
                    true,
                    100
                )
            )
        }
        initRecycler()
    }

    override fun setEvent() {
//        TODO("Not yet implemented")
    }

    private fun initRecycler() {
        resultPostRecyclerAdapter = ResultPostRecyclerAdapter(requireContext(), recyclerData)

        binding.rvPhotographerResult.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = resultPostRecyclerAdapter
        }
    }
}