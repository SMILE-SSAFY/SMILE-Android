package com.ssafy.smile.presentation.view.home

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.databinding.FragmentRecommendResultBinding
import com.ssafy.smile.domain.model.CustomPhotographerDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.HomeRecyclerAdapter
import com.ssafy.smile.presentation.adapter.RecommendResultAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.home.HomeViewModel
import com.ssafy.smile.presentation.viewmodel.home.RecommendViewModel

private const val TAG = "RecommendResultFragment_스마일"
class RecommendResultFragment : BaseFragment<FragmentRecommendResultBinding>(FragmentRecommendResultBinding::bind, R.layout.fragment_recommend_result) {

    private val recommendViewModel: RecommendViewModel by viewModels()
    private lateinit var recommendRecyclerAdapter: RecommendResultAdapter
    private val recyclerData = mutableListOf<CustomPhotographerDomainDto>()
    private val args: RecommendResultFragmentArgs by navArgs()

    override fun initView() {
        initToolbar()
        recommendViewModel.getPhotographerRecommendInfo(args.address)
        setObserver()
        initRecycler()
    }

    private fun setObserver() {
        recommendViewModel.getPhotographerRecommendResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()
                    if (it.data.size == 0) {
                        //TODO : 결과 없을 때 화면 구현
                    } else {
                        recyclerData.clear()
                        it.data.forEach { data ->
                            recyclerData.add(data.toCustomPhotographerDomainDto())
                        }
                        recommendRecyclerAdapter.notifyDataSetChanged()
                    }
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    showToast(requireContext(), "작가 추천 목록 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
            }
        }
    }

    private fun initRecycler() {
        recommendRecyclerAdapter = RecommendResultAdapter(requireContext(), recyclerData).apply {
            setPhotographerHeartItemClickListener(object : RecommendResultAdapter.OnPhotographerHeartItemClickListener{
                override fun onClick(view: View, position: Int) {
                    recommendViewModel.photographerHeart(recyclerData[position].photographerId)
                }
            })
            setItemClickListener(object : RecommendResultAdapter.OnItemClickListener{
                override fun onClick(view: View, position: Int) {
                    val action = RecommendResultFragmentDirections.actionRecommendResultFragmentToPortfolioGraph(photographerId = recyclerData[position].photographerId, postId = -1L)
                    findNavController().navigate(action)
                }

            })
        }

        binding.rvRecommend.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recommendRecyclerAdapter
        }
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("작가 추천", true) { moveToPopUpSelf() }
    }

    private fun moveToPopUpSelf() = findNavController().navigate(R.id.action_recommendResultFragment_pop)

    override fun setEvent() {
        binding.btnHome.setOnClickListener {
            findNavController().navigate(R.id.action_recommendResultFragment_to_mainFragment)
        }
    }
}