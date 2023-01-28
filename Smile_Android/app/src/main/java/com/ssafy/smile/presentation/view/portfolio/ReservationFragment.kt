package com.ssafy.smile.presentation.view.portfolio

import android.graphics.Color
import androidx.appcompat.widget.Toolbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentReservationBinding
import com.ssafy.smile.presentation.base.BaseFragment

class ReservationFragment : BaseFragment<FragmentReservationBinding>(FragmentReservationBinding::bind, R.layout.fragment_reservation) {

    var isTimeChecked = false

    override fun initView() {
        initToolbar()
    }

    private fun initToolbar() {
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("예약하기", true)
    }

    override fun setEvent() {
        binding.apply {
            btnTime.setOnClickListener {
                showTimePicker()
            }
        }
    }

    private fun showTimePicker() {
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setInputMode(INPUT_MODE_KEYBOARD)
            .setPositiveButtonText("선택")
            .setTitleText("시작 시간을 선택하세요")
            .build()

        timePicker.addOnPositiveButtonClickListener {
            setTimeText(timePicker.hour, timePicker.minute)
            isTimeChecked = true
        }

        timePicker.show(requireFragmentManager(), "time picker tag")
    }

    private fun setTimeText(hour: Int, minute: Int) {
        val ampm = if (hour > 12) {
            "오후"
        } else {
            "오전"
        }
        val hour = String.format("%02d", hour % 12)
        val min = String.format("%02d", minute)

        binding.apply {
            tvTime.setTextColor(Color.BLACK)
            tvTime.text = "${ampm} ${hour}시 ${min}분"
        }
    }
}