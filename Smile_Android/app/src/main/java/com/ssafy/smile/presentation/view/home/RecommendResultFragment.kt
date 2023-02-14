package com.ssafy.smile.presentation.view.home

import android.os.Parcelable
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smile.R
import com.ssafy.smile.common.util.CommonUtils
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
    private var recyclerViewState: Parcelable? = null

    override fun onResume() {
        super.onResume()

        if (recyclerViewState != null) {
            CommonUtils.setSavedRecyclerViewState(recyclerViewState, binding.rvRecommend)
        }
    }

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
                    if (it.data.isHeartEmpty) {
                        setIsEmptyView(View.VISIBLE, View.GONE, "작가 추천을 위한 데이터가 충분하지 않습니다.\n좋아하는 작가에게 하트를 눌러주세요:)")
                    } else {
                        if (it.data.photographerInfoList.size == 0) {
                            setIsEmptyView(View.VISIBLE, View.GONE, "추천 결과가 존재하지 않습니다")
                        } else {
                            recyclerData.clear()
                            for (i in 0 until it.data.photographerInfoList.size) {
                                recyclerData.add(it.data.toCustomPhotographerDomainDto(i))
                            }
                            recommendRecyclerAdapter.notifyDataSetChanged()
                        }
                    }
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    showToast(requireContext(), "작가 추천 목록 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.ERROR)
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
                    recyclerViewState = CommonUtils.saveRecyclerViewState(binding.rvRecommend)
                    val action = RecommendResultFragmentDirections.actionRecommendResultFragmentToPortfolioGraph(photographerId = recyclerData[position].photographerId, postId = -1L, goToDetail = false)
                    findNavController().navigate(action)
                }

            })
        }

        binding.rvRecommend.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recommendRecyclerAdapter
        }
    }

    private fun setIsEmptyView(emptyView: Int, recyclerView: Int, emptyViewText: String?) {
        binding.apply {
            layoutEmptyView.layoutEmptyView.visibility = emptyView
            layoutEmptyView.tvEmptyView.text = emptyViewText
            rvRecommend.visibility = recyclerView
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