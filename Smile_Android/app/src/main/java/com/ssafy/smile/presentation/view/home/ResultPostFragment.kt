package com.ssafy.smile.presentation.view.home

import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smile.R
import com.ssafy.smile.common.util.CommonUtils
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
    private var recyclerViewState: Parcelable? = null

    override fun onResume() {
        super.onResume()
        setObserver()
        initRecycler()

        if (recyclerViewState != null) {
            CommonUtils.setSavedRecyclerViewState(recyclerViewState, binding.rvPostResult)
        }
    }

    override fun initView() {
        setObserver()
        initRecycler()
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

                    if (it.data.size == 0) {
                        recyclerData.clear()
                        resultPostRecyclerAdapter.notifyDataSetChanged()
                        setIsEmptyView(View.VISIBLE, View.GONE, "검색 결과가 존재하지 않습니다")
                    } else {
                        recyclerData.clear()
                        it.data.forEach { data ->
                            recyclerData.add(data.toCustomPostDomainDto())
                        }
                        resultPostRecyclerAdapter.notifyDataSetChanged()
                        setIsEmptyView(View.GONE, View.VISIBLE, null)
                    }
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    if (it.errorCode != 404) {
                        showToast(requireContext(), "게시물 검색 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.ERROR)
                    }
                }
            }
        }
    }

    private fun setIsEmptyView(emptyView: Int, recyclerView: Int, emptyViewText: String?) {
        binding.apply {
            layoutEmptyView.layoutEmptyView.visibility = emptyView
            layoutEmptyView.tvEmptyView.text = emptyViewText
            rvPostResult.visibility = recyclerView
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
                    showToast(requireContext(), "게시물 좋아요 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.ERROR)
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
                    recyclerViewState = CommonUtils.saveRecyclerViewState(binding.rvPostResult)
                    val action = SearchFragmentDirections.actionSearchFragmentToPortfolioGraph(photographerId = -1L, postId = recyclerData[position].articleId)
                    findNavController().navigate(action)
                }
            })
        }

        binding.rvPostResult.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = resultPostRecyclerAdapter
        }
    }
}