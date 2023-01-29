package com.ssafy.smile.presentation.view.portfolio

import android.graphics.Color
import androidx.appcompat.widget.Toolbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import com.ssafy.smile.R
import com.ssafy.smile.common.view.CommonDialog
import com.ssafy.smile.databinding.FragmentReservationBinding
import com.ssafy.smile.domain.model.DialogBody
import com.ssafy.smile.presentation.base.BaseFragment
import java.time.LocalDate

class ReservationFragment : BaseFragment<FragmentReservationBinding>(FragmentReservationBinding::bind, R.layout.fragment_reservation) {

    var isDateChecked = true
    var isTimeChecked = true
    var isPlaceChecked = true
    var isCategoryChecked = true

    override fun initView() {
        initToolbar()
    }

    private fun initToolbar() {
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("예약하기", true)
    }

    override fun setEvent() {
        binding.apply {
            btnDate.setOnClickListener {
                showDatePicker()
            }
            btnTime.setOnClickListener {
                showTimePicker()
            }
            btnPay.setOnClickListener {
                if (isAllInput()) {
                    showEmailCheckDialog()
                }
            }
        }
    }

    private fun showDatePicker() {
        val unable = arrayListOf<LocalDate>(
            LocalDate.of(2023, 1, 29),
            LocalDate.of(2023, 2, 1),
            LocalDate.of(2023, 2, 2),
            LocalDate.of(2023, 2, 16),
            LocalDate.of(2023, 2, 17),
        )

        CustomCalendarDialog(requireContext(), unable).apply {
            setButtonClickListener(object : CustomCalendarDialog.OnButtonClickListener{
                override fun onOkButtonClick(year: String, date: String) {
                    setDateText(year, date)
                }
            })
            show()
        }
    }

    private fun setDateText(year: String, date: String) {
        binding.apply {
            tvDate.setTextColor(Color.BLACK)
            tvDate.text = "$year $date"
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

    private fun isAllInput(): Boolean {
        return isDateChecked && isTimeChecked && isPlaceChecked && isCategoryChecked
    }

    private fun showEmailCheckDialog() {
        binding.apply {
            val dialog = CommonDialog(
                requireContext(),
                DialogBody("잠깐!\n사진을 전송 받을 이메일이 맞나요?\n\n${etEmail.text}", "맞아요", "틀려요"),
                { showToast(requireContext(), "결제 페이지로 이동~") },
                { showToast(requireContext(), "이메일을 다시 입력해주세요") }
            )
            showDialog(dialog, viewLifecycleOwner)
        }
    }
}