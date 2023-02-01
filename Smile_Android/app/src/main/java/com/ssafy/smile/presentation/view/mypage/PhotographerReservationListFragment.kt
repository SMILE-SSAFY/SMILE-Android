package com.ssafy.smile.presentation.view.mypage

import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentPhotographerReservationListBinding
import com.ssafy.smile.presentation.base.BaseFragment

class PhotographerReservationListFragment : BaseFragment<FragmentPhotographerReservationListBinding>(FragmentPhotographerReservationListBinding::bind, R.layout.fragment_reservation_result) {
    override fun initView() {
        initToolbar()
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("마이페이지", true) { moveToPopUpSelf() }
    }

    private fun moveToPopUpSelf() = findNavController().navigate(R.id.action_customerReservationListFragment_pop)

    override fun setEvent() {
    }
}