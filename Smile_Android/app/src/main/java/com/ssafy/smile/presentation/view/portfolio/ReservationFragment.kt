package com.ssafy.smile.presentation.view.portfolio

import androidx.appcompat.widget.Toolbar
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentReservationBinding
import com.ssafy.smile.presentation.base.BaseFragment

class ReservationFragment : BaseFragment<FragmentReservationBinding>(FragmentReservationBinding::bind, R.layout.fragment_reservation) {
    override fun initView() {
        initToolbar()
    }

    private fun initToolbar() {
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("예약하기", true)
    }

    override fun setEvent() {
//        TODO("Not yet implemented")
    }
}