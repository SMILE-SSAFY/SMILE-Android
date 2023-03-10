package com.ssafy.smile.presentation.view.portfolio

import android.view.View
import androidx.navigation.navGraphViewModels
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.CommonDialog
import com.ssafy.smile.databinding.FragmentReviewViewPagerBinding
import com.ssafy.smile.domain.model.DialogBody
import com.ssafy.smile.domain.model.PhotographerReviewDomainDto
import com.ssafy.smile.presentation.adapter.PhotographerReviewRVAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.portfolio.PortfolioViewModel

class ReviewViewPagerFragment : BaseFragment<FragmentReviewViewPagerBinding>(FragmentReviewViewPagerBinding::bind, R.layout.fragment_review_view_pager) {
    private val viewModel: PortfolioViewModel by navGraphViewModels(R.id.portfolioGraph)
    private lateinit var photographerReviewRVAdapter: PhotographerReviewRVAdapter

    override fun initView() {

        setObserver()
        initRVView()
    }

    override fun setEvent() { }

    private fun setObserver() {
        viewModel.apply {
            photographerReviewListResponse.observe(viewLifecycleOwner){
                when(it){
                    is NetworkUtils.NetworkResponse.Loading -> {
                        dismissLoadingDialog()
                    }
                    is NetworkUtils.NetworkResponse.Success -> {
                        dismissLoadingDialog()
                        if (it.data.isEmpty()) {
                            setEmptyView(true)
                            setRVView(false)
                        }
                        else{
                            val dataList = it.data.map {dto -> dto.makeToDomainDto() }
                            setEmptyView(false)
                            setRVView(true, dataList as ArrayList<PhotographerReviewDomainDto>)
                        }
                    }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        showLoadingDialog(requireContext())
                    }
                }
            }
            deleteReviewResponse.observe(viewLifecycleOwner){
                when(it){
                    is NetworkUtils.NetworkResponse.Loading -> { }
                    is NetworkUtils.NetworkResponse.Success -> {
                        viewModel.getPhotographerReviewList(photographerId)
                    }
                    is NetworkUtils.NetworkResponse.Failure -> { }
                }
            }
            viewModel.getPhotographerReviewList(viewModel.photographerId)
        }
    }

    private fun initRVView() {
        photographerReviewRVAdapter = PhotographerReviewRVAdapter().apply {
            setItemClickListener(object : PhotographerReviewRVAdapter.ItemClickListener {
                override fun onClickDelete(view: View, position: Int, reviewDto: PhotographerReviewDomainDto) {
                    showReviewDeleteDialog(reviewDto.reviewId)
                }
            })
        }
        binding.rvReview.adapter = photographerReviewRVAdapter
    }

    private fun setEmptyView(isSet : Boolean){
        if (isSet){
            binding.layoutEmptyView.layoutEmptyView.visibility = View.VISIBLE
            binding.layoutEmptyView.tvEmptyView.text = "?????? ????????? ????????? ???????????? ????????????."
        }else binding.layoutEmptyView.layoutEmptyView.visibility = View.GONE
    }

    private fun setRVView(isSet : Boolean, itemList : ArrayList<PhotographerReviewDomainDto> = arrayListOf()){
        if (isSet) {
            binding.rvReview.visibility = View.VISIBLE
            photographerReviewRVAdapter.setListData(itemList)
        } else binding.rvReview.visibility = View.GONE
    }

    private fun showReviewDeleteDialog(reviewId : Long){
        val dialog = CommonDialog(requireContext(), DialogBody(resources.getString(R.string.delete_review), "??????"), { viewModel.deleteReview(reviewId) })
        showDialog(dialog, viewLifecycleOwner)
    }

}