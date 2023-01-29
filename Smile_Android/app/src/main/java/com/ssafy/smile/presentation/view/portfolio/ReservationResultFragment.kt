package com.ssafy.smile.presentation.view.portfolio

import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentReservationResultBinding
import com.ssafy.smile.domain.model.CustomReservationDomainDto
import com.ssafy.smile.presentation.base.BaseFragment

class ReservationResultFragment : BaseFragment<FragmentReservationResultBinding>(FragmentReservationResultBinding::bind, R.layout.fragment_reservation_result) {
    override fun initView() {
        initToolbar()
        setResult()
    }

    private fun setResult() {
        binding.apply {
            customReservation.setAttrs(
                CustomReservationDomainDto(
                    "2023년 01월 07일",
                    "작가님",
                    "홍길동",
                    "010-1234-5678",
                    "2023년 01월 09일(월)",
                    "오전 09시 30분",
                    "구미시\n동락공원 풍차 앞",
                    "웨딩사진\n기본 300장 + 추가 100장 + 보정 20장",
                    "300,000원"
                )
            )
        }
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("예약하기", false)
    }

    override fun setEvent() {
        binding.btnHome.setOnClickListener {
            findNavController().navigate(R.id.action_reservationResultFragment_to_mainFragment)
        }
    }
}