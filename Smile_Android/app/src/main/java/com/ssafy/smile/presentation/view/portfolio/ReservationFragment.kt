package com.ssafy.smile.presentation.view.portfolio

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import com.ssafy.smile.R
import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.CommonDialog
import com.ssafy.smile.data.remote.model.ResCategory
import com.ssafy.smile.data.remote.model.ReservationRequestDto
import com.ssafy.smile.databinding.FragmentReservationBinding
import com.ssafy.smile.domain.model.DialogBody
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.portfolio.PortfolioGraphViewModel
import java.util.*
import java.util.regex.Pattern
import kr.co.bootpay.android.*;
import kr.co.bootpay.android.events.BootpayEventListener
import kr.co.bootpay.android.models.BootExtra
import kr.co.bootpay.android.models.BootItem
import kr.co.bootpay.android.models.Payload
import org.json.JSONException
import org.json.JSONObject

private const val TAG = "ReservationFragment_스마일"
class ReservationFragment : BaseFragment<FragmentReservationBinding>(FragmentReservationBinding::bind, R.layout.fragment_reservation) {

    private val args: ReservationFragmentArgs by navArgs()
    private val portfolioGraphViewModel: PortfolioGraphViewModel by navGraphViewModels(R.id.portfolioGraph)
    private var photographerId: Long = -1L
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
    var selectData = ReservationRequestDto()

    override fun initView() {
        initToolbar()
        setPhotographerId()
        portfolioGraphViewModel.getPhotographerReservationInfo(photographerId)
        setObserver()
    }

    private fun setPhotographerId() {
        photographerId = args.photographerId
        selectData.photographerId = photographerId
    }

    private fun initToolbar() {
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("예약하기", true) { moveToPopUpSelf() }
    }

    private fun moveToPopUpSelf() = findNavController().navigate(R.id.action_reservationFragment_pop)

    private fun setObserver() {
        getPhotographerReservationInfoResponseObserver()
        postReservationResponseObserver()
    }

    private fun getPhotographerReservationInfoResponseObserver() {
        portfolioGraphViewModel.photographerReservationResponse.observe(viewLifecycleOwner){
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
                        places.add(place.place)
                    }
                    it.data.categories.forEach { category ->
                        categories.add(category)
                    }
                    initPlaceSpinner()
                    initCategorySpinner()
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    Log.d(TAG, "photographerReservationResponseObserver: ${it.errorCode}")
                    showToast(requireContext(), "작가 예약 정보 조회 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
            }
        }
    }

    private fun postReservationResponseObserver() {
        portfolioGraphViewModel.postReservationResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()
                    val action = ReservationFragmentDirections.actionReservationFragmentToReservationResultFragment(it.data.toCustomReservationDomainDto())
                    findNavController().navigate(action)
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    showToast(requireContext(), "예약 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
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
        categoryData.clear()
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
            btnCancel.setOnClickListener {
                val dialog = CommonDialog(requireContext(), DialogBody("예약을 취소하시겠습니까?", "예", "아니요"),
                    { moveToPopUpSelf() })
                showDialog(dialog, viewLifecycleOwner)
            }
            tvPlace.setOnItemClickListener { _, _, _, _ ->
                selectData.address = tvPlace.text.toString()
                etPlaceDetail.isEnabled = true
            }
            tvCategory.setOnItemClickListener { _, _, pos, _ ->
                selectData.categoryName = tvCategory.text.toString()
                tvOption.isEnabled = true

                optionData.clear()
                priceData.clear()
                categories[pos].details.forEach { categoryDetail ->
                    optionData.add(categoryDetail.options)
                    priceData.add(categoryDetail.price)
                }
                initOptionSpinner()
            }
            tvOption.setOnItemClickListener { _, _, pos, _ ->
                isCategoryChecked = true

                selectData.options = optionData[pos]
                selectData.price = priceData[pos]
                binding.tvCost.text = "${CommonUtils.makeComma(priceData[pos])} 원"
            }
        }
    }

    private fun showDatePicker() {
        CustomCalendarDialog(requireContext(), reservedDate).apply {
            setButtonClickListener(object : CustomCalendarDialog.OnButtonClickListener{
                override fun onOkButtonClick(year: String, date: String) {
                    isDateChecked = true

                    selectData.date = "${year.substring(0, 4)}-${date.substring(0, 2)}-${date.substring(4, 6)}"
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
            selectData.time = "${String.format("%02d", timePicker.hour)}:${String.format("%02d", timePicker.minute)}"
            setTimeText(timePicker.hour, timePicker.minute)
            isTimeChecked = true
        }

        timePicker.show(requireFragmentManager(), "time picker tag")
    }

    private fun setTimeText(hour: Int, minute: Int) {
        val ampm = if (hour >= 12) {
            "오후"
        } else {
            "오전"
        }
        val tHour = if (hour % 12 == 0) {
            "12"
        } else {
            String.format("%02d", hour % 12)
        }
        val min = String.format("%02d", minute)

        binding.apply {
            tvTime.setTextColor(Color.BLACK)
            tvTime.text = "${ampm} ${tHour}시 ${min}분"
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
                { goBootPayRequest(getItemInfo()) },
                { showToast(requireContext(), "이메일을 다시 입력해주세요") }
            )
            showDialog(dialog, viewLifecycleOwner)
        }
    }

    private fun getItemInfo(): BootItem {
        val item = BootItem().apply {
            id = "SMILE_ITEM_ID"
            name = selectData.categoryName + selectData.options
            qty = 1
            price = selectData.price.toDouble()
        }
        return item
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
                        "detailPlace" -> {
                            selectData.detailAddress = editText.text.toString()
                            isPlaceChecked = true
                        }
                        "email" -> {
                            selectData.email = editText.text.toString()
                            isEmailChecked = true
                        }
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

    private fun goBootPayRequest(itemInfo: BootItem) {
        val extra = BootExtra().setCardQuota("0,2,3,6,12") // 5만원 이상 구매시 할부허용 범위
        val items: MutableList<BootItem> = arrayListOf(itemInfo)
        val payload = Payload().apply {
            applicationId = getString(R.string.bootpay_key)
            orderName = itemInfo.name
            orderId = "SMILE_ORDER_ID"
            price = itemInfo.price
            setExtra(extra).items = items
        }

        bootPayEvent(payload)
    }

    private fun bootPayEvent(payload: Payload) {
        Bootpay.init(requireFragmentManager(), requireContext())
            .setPayload(payload)
            .setEventListener(object : BootpayEventListener {
                override fun onCancel(data: String) {
                    Log.d(TAG, "bootpay cancel: $data")
                }

                override fun onError(data: String) {
                    Log.d(TAG, "bootpay error: $data")
                }

                override fun onClose() {
                    Log.d(TAG, "bootpay close")
                    Bootpay.removePaymentWindow()
                }

                override fun onIssued(data: String) {
                    Log.d(TAG, "bootpay issued: $data")
                }

                override fun onConfirm(data: String): Boolean {
                    Log.d(TAG, "bootpay confirm: $data")
                    return true
                }

                override fun onDone(data: String) {
                    Log.d("bootpay done", data)
                    getReceiptId(data)
                }
            }).requestPayment()
    }

    private fun getReceiptId(data: String) {
        try {
            val payData = JSONObject(data).getJSONObject("data")
            val receiptId = payData.getString("receipt_id")

            Log.d(TAG, "getReceiptId: $receiptId")
            selectData.receiptId = receiptId
            portfolioGraphViewModel.postReservation(selectData)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}