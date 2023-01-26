package com.ssafy.smile.presentation.view.home

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.databinding.FragmentResultPhotographerBinding
import com.ssafy.smile.domain.model.CustomPhotographerDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.ResultPhotographerRecyclerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.home.SearchViewModel

class ResultPhotographerFragment : BaseFragment<FragmentResultPhotographerBinding>(FragmentResultPhotographerBinding::bind, R.layout.fragment_result_photographer) {

    private val searchViewModel by activityViewModels<SearchViewModel>()
    private lateinit var resultPhotographerRecyclerAdapter: ResultPhotographerRecyclerAdapter
    private val recyclerData = mutableListOf<CustomPhotographerDomainDto>()

    override fun initView() {
        setObserver()
        initRecycler()
    }

    private fun setObserver() {
        searchPhotographerResponseObserver()
    }

    private fun searchPhotographerResponseObserver() {
        searchViewModel.searchPhotographerResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()
                    it.data.forEach { data ->
                        recyclerData.add(data.toCustomPhotographerDomainDto())
                    }
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    showToast(requireContext(), "작가 검색 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
            }
        }
    }

    override fun setEvent() {
    }

    private fun initRecycler() {
        resultPhotographerRecyclerAdapter = ResultPhotographerRecyclerAdapter(requireContext(), recyclerData)

        binding.rvPhotographerResult.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = resultPhotographerRecyclerAdapter
        }
    }
}