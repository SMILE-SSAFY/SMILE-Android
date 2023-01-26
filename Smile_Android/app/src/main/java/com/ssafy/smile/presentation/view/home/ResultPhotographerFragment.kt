package com.ssafy.smile.presentation.view.home

import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentResultPhotographerBinding
import com.ssafy.smile.domain.model.CustomPhotographerDomainDto
import com.ssafy.smile.presentation.adapter.ResultPhotographerRecyclerAdapter
import com.ssafy.smile.presentation.base.BaseFragment

class ResultPhotographerFragment : BaseFragment<FragmentResultPhotographerBinding>(FragmentResultPhotographerBinding::bind, R.layout.fragment_result_photographer) {

    private lateinit var resultPhotographerRecyclerAdapter: ResultPhotographerRecyclerAdapter
    private val recyclerData = mutableListOf<CustomPhotographerDomainDto>()

    override fun initView() {
        for (i in 1..20) {
            recyclerData.add(CustomPhotographerDomainDto(
                "http://image.dongascience.com/Photo/2020/03/5bddba7b6574b95d37b6079c199d7101.jpg",
                "애견 사진",
                "홍길동",
                "구미시",
                "100,000원 부터~",
                true,
                200
            ))
        }
        initRecycler()
    }

    override fun setEvent() {
//        TODO("Not yet implemented")
    }

    private fun initRecycler() {
        resultPhotographerRecyclerAdapter = ResultPhotographerRecyclerAdapter(requireContext(), recyclerData)

        binding.rvPhotographerResult.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = resultPhotographerRecyclerAdapter
        }
    }
}