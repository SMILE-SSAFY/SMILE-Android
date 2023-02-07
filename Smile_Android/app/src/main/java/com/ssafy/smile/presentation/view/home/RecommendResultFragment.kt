package com.ssafy.smile.presentation.view.home

import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentRecommendResultBinding
import com.ssafy.smile.presentation.base.BaseFragment

private const val TAG = "RecommendResultFragment_스마일"
class RecommendResultFragment : BaseFragment<FragmentRecommendResultBinding>(FragmentRecommendResultBinding::bind, R.layout.fragment_recommend_result) {

    private val args: RecommendResultFragmentArgs by navArgs()

    override fun initView() {
        initToolbar()
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