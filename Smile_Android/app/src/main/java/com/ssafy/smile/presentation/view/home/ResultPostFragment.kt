package com.ssafy.smile.presentation.view.home

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var resultPostRecyclerAdapter: ResultPostRecyclerAdapter
    private val recyclerData = mutableListOf<CustomPostDomainDto>()

    override fun onResume() {
        super.onResume()
        setObserver()
        initRecycler()
    }

    override fun initView() {
    }

    private fun setObserver() {
        searchPostResponseObserver()
        postHeartResponseObserver()
    }

    private fun searchPostResponseObserver() {
        searchViewModel.searchPostResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()
                    binding.apply {
                        tvResult.text = "'${searchViewModel.searchCategory}'로 검색한 결과입니다"
                    }
                    recyclerData.clear()
                    it.data.forEach { data ->
                        recyclerData.add(data.toCustomPostDomainDto())
                    }
                    resultPostRecyclerAdapter.notifyDataSetChanged()
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    if (it.errorCode == 404) {
                        showToast(requireContext(), "검색한 키워드의 게시물이 존재하지 않습니다.", Types.ToastType.INFO)
                    } else {
                        showToast(requireContext(), "게시물 검색 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                    }
                }
            }
        }
    }

    private fun postHeartResponseObserver() {
        searchViewModel.postHeartResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Loading -> {
                }
                is NetworkUtils.NetworkResponse.Success -> {
                    searchViewModel.searchPost(searchViewModel.searchCategory)
                    resultPostRecyclerAdapter.notifyDataSetChanged()
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    showToast(requireContext(), "게시물 좋아요 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
            }
        }
    }

    override fun setEvent() {
    }

    private fun initRecycler() {
        resultPostRecyclerAdapter = ResultPostRecyclerAdapter(requireContext(), recyclerData).apply {
            setPostHeartItemClickListener(object : ResultPostRecyclerAdapter.OnPostHeartItemClickListener{
                override fun onClick(view: View, position: Int) {
                    searchViewModel.postHeart(recyclerData[position].articleId)
                }
            })
            setItemClickListener(object: ResultPostRecyclerAdapter.OnItemClickListener{
                override fun onClick(view: View, position: Int) {
                    val action = SearchFragmentDirections.actionSearchFragmentToPostDetailFragment(recyclerData[position].articleId)
                    findNavController().navigate(action)
                }

            })
        }

        binding.rvPhotographerResult.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = resultPostRecyclerAdapter
        }
    }
}