package com.ssafy.smile.presentation.view.home

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.databinding.FragmentResultPhotographerBinding
import com.ssafy.smile.domain.model.CustomPhotographerDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.ResultPhotographerRecyclerAdapter
import com.ssafy.smile.presentation.adapter.ResultPostRecyclerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.home.HomeViewModel
import com.ssafy.smile.presentation.viewmodel.home.SearchViewModel

private const val TAG = "ResultPhotographerFragm_스마일"
class ResultPhotographerFragment : BaseFragment<FragmentResultPhotographerBinding>(FragmentResultPhotographerBinding::bind, R.layout.fragment_result_photographer) {

    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var resultPhotographerRecyclerAdapter: ResultPhotographerRecyclerAdapter
    private val recyclerData = mutableListOf<CustomPhotographerDomainDto>()

    override fun onResume() {
        super.onResume()
        setObserver()
        initRecycler()
    }

    override fun initView() {
        setObserver()
        initRecycler()
    }

    private fun setObserver() {
        searchPhotographerResponseObserver()
        photographerHeartResponseObserver()
    }

    private fun searchPhotographerResponseObserver() {
        searchViewModel.searchPhotographerResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()

                    if (it.data.size == 0) {
                        recyclerData.clear()
                        resultPhotographerRecyclerAdapter.notifyDataSetChanged()
                        setIsEmptyView(View.VISIBLE, View.GONE, "검색 결과가 존재하지 않습니다")
                    } else {
                        recyclerData.clear()
                        it.data.forEach { data ->
                            recyclerData.add(data.toCustomPhotographerDomainDto())
                        }
                        resultPhotographerRecyclerAdapter.notifyDataSetChanged()
                        setIsEmptyView(View.GONE, View.VISIBLE, null)
                    }
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    if (it.errorCode == 404) {
                        showToast(requireContext(), "검색한 키워드의 작가가 존재하지 않습니다.", Types.ToastType.INFO)
                    } else {
                        showToast(requireContext(), "작가 검색 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                    }
                }
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
            }
        }
    }

    private fun setIsEmptyView(emptyView: Int, recyclerView: Int, emptyViewText: String?) {
        binding.apply {
            layoutEmptyView.layoutEmptyView.visibility = emptyView
            layoutEmptyView.tvEmptyView.text = emptyViewText
            rvPhotographerResult.visibility = recyclerView
        }
    }

    private fun photographerHeartResponseObserver() {
        searchViewModel.photographerHeartResponse.observe(viewLifecycleOwner) {
            Log.d(TAG, "photographerHeartResponseObserver: observer")
            when(it) {
                is NetworkUtils.NetworkResponse.Loading -> {
                    Log.d(TAG, "photographerHeartResponseObserver: loading")
                }
                is NetworkUtils.NetworkResponse.Success -> {
                    Log.d(TAG, "photographerHeartResponseObserver: success")
                    resultPhotographerRecyclerAdapter.notifyDataSetChanged()
                    searchViewModel.searchPhotographer(searchViewModel.searchCategory.value.toString())
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    Log.d(TAG, "photographerHeartResponseObserver: failure")
                    showToast(requireContext(), "작가 좋아요 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
            }
        }
    }

    override fun setEvent() {
    }

    private fun initRecycler() {
        resultPhotographerRecyclerAdapter = ResultPhotographerRecyclerAdapter(requireContext(), recyclerData).apply {
            setPhotographerHeartItemClickListener(object : ResultPhotographerRecyclerAdapter.OnPhotographerHeartItemClickListener{
                override fun onClick(view: View, position: Int) {
                    searchViewModel.photographerHeart(recyclerData[position].photographerId)
                }
            })
            setItemClickListener(object : ResultPhotographerRecyclerAdapter.OnItemClickListener{
                override fun onClick(view: View, position: Int) {
                    val action = SearchFragmentDirections.actionSearchFragmentToPortfolioGraph(recyclerData[position].photographerId)
                    findNavController().navigate(action)
                }

            })
        }

        binding.rvPhotographerResult.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = resultPhotographerRecyclerAdapter
        }
    }
}