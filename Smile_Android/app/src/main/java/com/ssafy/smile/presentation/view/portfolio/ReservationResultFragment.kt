package com.ssafy.smile.presentation.view.portfolio

import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentReservationResultBinding
import com.ssafy.smile.domain.model.CustomReservationDomainDto
import com.ssafy.smile.presentation.base.BaseFragment

class ReservationResultFragment : BaseFragment<FragmentReservationResultBinding>(FragmentReservationResultBinding::bind, R.layout.fragment_reservation_result) {

    private val args: ReservationResultFragmentArgs by navArgs()

    override fun initView() {
        initToolbar()
        setResult()
    }

    private fun setResult() {
        binding.apply {
            customReservation.setAttrs(
                CustomReservationDomainDto(
                    args.reservationResult.reservationId,
                    args.reservationResult.opposite,
                    args.reservationResult.name,
                    args.reservationResult.phoneNumber,
                    args.reservationResult.resDate,
                    args.reservationResult.startTime,
                    args.reservationResult.location,
                    args.reservationResult.category,
                    args.reservationResult.cost
                ),
                false
            )
        }
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("예약하기", false) { moveToPopUpSelf() }
    }

    private fun moveToPopUpSelf() = findNavController().navigate(R.id.action_reservationResultFragment_pop)

    override fun setEvent() {
        binding.btnHome.setOnClickListener {
            findNavController().navigate(R.id.action_reservationResultFragment_to_mainFragment)
        }
    }
}