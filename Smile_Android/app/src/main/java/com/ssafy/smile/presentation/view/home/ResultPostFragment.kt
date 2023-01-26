package com.ssafy.smile.presentation.view.home

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.databinding.FragmentResultPostBinding
import com.ssafy.smile.domain.model.CustomPostDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.ResultPostRecyclerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.home.SearchViewModel

class ResultPostFragment : BaseFragment<FragmentResultPostBinding>(FragmentResultPostBinding::bind, R.layout.fragment_result_post) {

    private val searchViewModel by activityViewModels<SearchViewModel>()
    private lateinit var resultPostRecyclerAdapter: ResultPostRecyclerAdapter
    private val recyclerData = mutableListOf<CustomPostDomainDto>()

    override fun initView() {
        setObserver()
        initRecycler()
        binding.apply {
            tvResult.text = "'${searchViewModel.searchCategory}'로 검색한 결과입니다"
        }
    }

    private fun setObserver() {
        searchPostResponseObserver()
    }

    private fun searchPostResponseObserver() {
        searchViewModel.searchPostResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()
                    it.data.forEach { data ->
                        recyclerData.add(data.toCustomPostDomainDto())
                    }
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    showToast(requireContext(), "게시물 검색 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
            }
        }
    }

    override fun setEvent() {
    }

    private fun initRecycler() {
        resultPostRecyclerAdapter = ResultPostRecyclerAdapter(requireContext(), recyclerData)

        binding.rvPhotographerResult.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = resultPostRecyclerAdapter
        }
    }
}