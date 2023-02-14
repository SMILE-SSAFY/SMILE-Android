package com.ssafy.smile.common.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.chip.Chip
import com.ssafy.smile.R
import com.ssafy.smile.domain.model.CustomReservationDomainDto

class CustomReservation: ConstraintLayout {
    lateinit var tvOpposite: TextView
    lateinit var tvName: TextView
    lateinit var tvPhoneNumber: TextView
    lateinit var tvReservationDate: TextView
    lateinit var tvTime: TextView
    lateinit var tvPlace: TextView
    lateinit var tvCategory: TextView
    lateinit var tvCost: TextView
    lateinit var statusChip: Chip
    lateinit var btnResFix: Button
    lateinit var btnCancel: Button
    lateinit var btnPayFix: Button
    lateinit var btnReview: Button
    lateinit var btnReviewCheck: Button

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init()
        getAttrs(attrs)
    }

    private fun init() {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_reservation, this, false)
        addView(view)

        tvOpposite = findViewById(R.id.tv_opposite)
        tvName = findViewById(R.id.tv_name)
        tvPhoneNumber = findViewById(R.id.tv_phone_number)
        tvReservationDate = findViewById(R.id.tv_reservation_date)
        tvTime = findViewById(R.id.tv_time)
        tvPlace = findViewById(R.id.tv_place)
        tvCategory = findViewById(R.id.tv_category)
        tvCost = findViewById(R.id.tv_cost)
        statusChip = findViewById(R.id.chip_status)
        btnResFix = findViewById(R.id.btn_res_fix)
        btnCancel = findViewById(R.id.btn_cancel)
        btnPayFix = findViewById(R.id.btn_pay_fix)
        btnReview = findViewById(R.id.btn_review)
        btnReviewCheck = findViewById(R.id.btn_review_check)
    }

    // attrs.xml 파일로부터 속성 정보 확보 - typedArray
    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomReservation)
        setTypedArray(typedArray)
    }

    // 속성값을 view 요소들에 연결
    private fun setTypedArray(typedArray: TypedArray) {
        tvOpposite.text = typedArray.getText(R.styleable.CustomReservation_opposite)
        tvName.text = typedArray.getText(R.styleable.CustomReservation_rName)
        tvPhoneNumber.text = typedArray.getText(R.styleable.CustomReservation_phonNumber)
        tvReservationDate.text = typedArray.getText(R.styleable.CustomReservation_resDate)
        tvTime.text = typedArray.getText(R.styleable.CustomReservation_startTime)
        tvPlace.text = typedArray.getText(R.styleable.CustomReservation_rLocation)
        tvCategory.text = typedArray.getText(R.styleable.CustomReservation_rCategory)
        tvCost.text = typedArray.getText(R.styleable.CustomReservation_cost)
        statusChip.text = typedArray.getText(R.styleable.CustomReservation_status)

        typedArray.recycle()
    }

    fun setAttrs(data: CustomReservationDomainDto, isList: Boolean) {
        tvName.text = data.name
        tvPhoneNumber.text = data.phoneNumber
        tvReservationDate.text = data.resDate
        tvTime.text = data.startTime
        tvPlace.text = data.location
        tvCategory.text = data.category
        tvCost.text = data.cost

        if (isList) {
            setChips(data.status)
            setButtons(data.status, data.opposite, data.isReview)
        }

        invalidate()
        requestLayout()
    }

    fun setChips(status: String) {
        statusChip.visibility = View.VISIBLE
        when(status) {
            "예약확정전" -> setChipColor(status, R.color.blue200)
            "예약확정" -> setChipColor(status, R.color.blue400)
            "예약진행중" -> setChipColor(status, R.color.blue400)
            "완료" -> setChipColor(status, R.color.gray500)
            "예약취소" -> setChipColor(status, R.color.gray500)
        }
    }

    private fun setChipColor(statusText: String, color: Int) {
        statusChip.apply {
            text = statusText
            setTextColor(resources.getColorStateList(color))
            setChipStrokeColorResource(color)
        }
    }

    fun setButtons(status: String, opposite: String, isReview: Boolean) {
        when(opposite) {
            "작가님" -> setCustomerButtons(status, isReview)
            "고객님" -> setPhotographerButtons(status)
        }
    }

    private fun setCustomerButtons(status: String, isReview: Boolean) {
        when(status) {
            "예약확정전" -> setButtons(arrayListOf(btnCancel))
            "예약확정" -> setButtons(arrayListOf(btnCancel))
            "예약진행중" -> setButtons(arrayListOf(btnPayFix))
            "완료" -> {
                if (!isReview) {
                    setButtons(arrayListOf(btnReview))
                } else {
                    setButtons(arrayListOf(btnReviewCheck))
                }
            }
            "예약취소" -> setButtons(arrayListOf())
        }
    }

    private fun setPhotographerButtons(status: String) {
        when(status) {
            "예약확정전" -> setButtons(arrayListOf(btnCancel, btnResFix))
            "예약확정" -> setButtons(arrayListOf(btnCancel))
            "예약취소" -> setButtons(arrayListOf())
        }
    }

    private fun setButtons(btnList: ArrayList<Button>) {
        btnCancel.visibility = View.GONE
        btnPayFix.visibility = View.GONE
        btnResFix.visibility = View.GONE
        btnReview.visibility = View.GONE
        btnReviewCheck.visibility = View.GONE

        btnList.forEach { btn ->
            btn.visibility = View.VISIBLE
        }
    }
}