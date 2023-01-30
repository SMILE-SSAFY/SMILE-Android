package com.ssafy.smile.presentation.view.portfolio

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import com.ssafy.smile.R
import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.CommonDialog
import com.ssafy.smile.data.remote.model.ResCategory
import com.ssafy.smile.data.remote.model.ReservationPhotographerDto
import com.ssafy.smile.databinding.FragmentReservationBinding
import com.ssafy.smile.domain.model.DialogBody
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.portfolio.ReservationViewModel
import java.time.LocalDate
import java.util.*
import java.util.regex.Pattern

private const val TAG = "ReservationFragment_스마일"
class ReservationFragment : BaseFragment<FragmentReservationBinding>(FragmentReservationBinding::bind, R.layout.fragment_reservation) {

    private val reservationViewModel by activityViewModels<ReservationViewModel>()
    private var photographerId: Long = -1
    private val reservedDate = arrayListOf<Date>()
    private val places = arrayListOf<String>()
    private val categories = arrayListOf<ResCategory>()

    private lateinit var placeSpinnerAdapter: ArrayAdapter<String>
    private lateinit var categorySpinnerAdapter: ArrayAdapter<String>
    private val categoryData = mutableListOf<String>()
    private lateinit var optionSpinnerAdapter: ArrayAdapter<String>
    private val optionData = mutableListOf<String>()
    private val priceData = mutableListOf<Int>()

    var isDateChecked = false
    var isTimeChecked = false
    var isPlaceChecked = false
    var isCategoryChecked = false
    var isEmailChecked = false

    override fun initView() {
        initToolbar()
        setPhotographerId()
        reservationViewModel.getPhotographerReservationInfo(photographerId)
        setObserver()
        initPlaceSpinner()
        initCategorySpinner()
        initOptionSpinner()
    }

    private fun initToolbar() {
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("예약하기", true)
    }

    private fun setPhotographerId() {
        // TODO : 이전 화면에서 작가 id 넘겨준 값 가져와서 photographerId 변수 값 변경하기
    }

    private fun setObserver() {
        getPhotographerReservationInfoResponseObserver()
    }

    private fun getPhotographerReservationInfoResponseObserver() {
        reservationViewModel.photographerReservationResponse.observe(viewLifecycleOwner){
            when(it) {
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()
                    reservedDate.clear()
                    places.clear()
                    categories.clear()

                    it.data.days.forEach { date ->
                        reservedDate.add(date)
                    }
                    it.data.places.forEach { place ->
                        places.add(place)
                    }
                    it.data.categories.forEach { category ->
                        categories.add(category)
                    }
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    Log.d(TAG, "photographerReservationResponseObserver: ${it.errorCode}")
                    showToast(requireContext(), "작가 예약 정보 조회 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
            }
        }
    }

    private fun initPlaceSpinner() {
        placeSpinnerAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner, places)
        binding.apply {
            tvPlace.setAdapter(placeSpinnerAdapter)
        }
    }

    private fun initCategorySpinner() {
        categories.forEach { category ->
            categoryData.add(category.categoryName)
        }

        categorySpinnerAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner, categoryData)
        binding.apply {
            tvCategory.setAdapter(categorySpinnerAdapter)
        }
    }

    private fun initOptionSpinner() {
        optionSpinnerAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner, optionData)
        binding.apply {
            tvOption.setAdapter(optionSpinnerAdapter)
        }
    }

    override fun setEvent() {
        binding.apply {
            etChangedListener(etPlaceDetail, "detailPlace")
            etChangedListener(etEmail, "email")

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
            tvPlace.setOnItemClickListener { _, _, _, _ ->
                etPlaceDetail.isEnabled = true
            }
            tvCategory.setOnItemClickListener { _, _, pos, _ ->
                tvOption.isEnabled = true

                categories[pos].categoryDetail.forEach { categoryDetail ->
                    optionData.add(categoryDetail.description)
                    priceData.add(categoryDetail.price)
                }
            }
            tvOption.setOnItemClickListener { _, _, pos, _ ->
                isCategoryChecked = true

                binding.tvCost.text = "${CommonUtils.makeComma(priceData[pos])} 원"
            }
        }
    }

    private fun showDatePicker() {
        CustomCalendarDialog(requireContext(), reservedDate).apply {
            setButtonClickListener(object : CustomCalendarDialog.OnButtonClickListener{
                override fun onOkButtonClick(year: String, date: String) {
                    isDateChecked = true
                    setDateText(year, date)
                }
            })
            show()
        }
    }

    private fun setDateText(year: String, date: String) {
        binding.apply {
            tvReservationDate.setTextColor(Color.BLACK)
            tvReservationDate.text = "$year $date"
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
        return isDateChecked && isTimeChecked && isPlaceChecked && isCategoryChecked && isEmailChecked
    }

    private fun showEmailCheckDialog() {
        binding.apply {
            val dialog = CommonDialog(
                requireContext(),
                DialogBody("잠깐!\n사진을 전송 받을 이메일이 맞나요?\n\n${etEmail.text}", "맞아요", "틀려요"),
                { findNavController().navigate(R.id.action_reservationFragment_to_reservationResultFragment) },
                { showToast(requireContext(), "이메일을 다시 입력해주세요") }
            )
            showDialog(dialog, viewLifecycleOwner)
        }
    }

    private fun etChangedListener(editText: EditText, type: String) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (type == "email") {
                    val email = binding.etEmail.text.toString()
                    if (!checkEmailRule(email)) {
                        binding.etEmail.error = "올바른 이메일 주소를 입력해주세요"
                    }
                }
            }
            override fun afterTextChanged(editable: Editable) {
                if (editable.isNotEmpty()) {
                    when(type) {
                        "detailPlace" -> isPlaceChecked = true
                        "email" -> isEmailChecked = true
                        else -> {}
                    }
                }
            }
        })
    }

    private fun checkEmailRule(email: String): Boolean {
        val rule = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val pattern = Pattern.compile(rule)

        return pattern.matcher(email).find()
    }
}