package com.ssafy.smile.common.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ssafy.smile.R
import com.ssafy.smile.domain.model.CustomReservationDomainDto

class CustomReservation: ConstraintLayout {
    lateinit var tvCurDate: TextView
    lateinit var tvOpposite: TextView
    lateinit var tvName: TextView
    lateinit var tvPhoneNumber: TextView
    lateinit var tvReservationDate: TextView
    lateinit var tvTime: TextView
    lateinit var tvPlace: TextView
    lateinit var tvCategory: TextView
    lateinit var tvCost: TextView

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init()
        getAttrs(attrs)
    }

    private fun init() {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_reservation, this, false)
        addView(view)

        tvCurDate = findViewById(R.id.tv_cur_date)
        tvOpposite = findViewById(R.id.tv_opposite)
        tvName = findViewById(R.id.tv_name)
        tvPhoneNumber = findViewById(R.id.tv_phone_number)
        tvReservationDate = findViewById(R.id.tv_reservation_date)
        tvTime = findViewById(R.id.tv_time)
        tvPlace = findViewById(R.id.tv_place)
        tvCategory = findViewById(R.id.tv_category)
        tvCost = findViewById(R.id.tv_cost)
    }

    // attrs.xml 파일로부터 속성 정보 확보 - typedArray
    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomReservation)
        setTypedArray(typedArray)
    }

    // 속성값을 view 요소들에 연결
    private fun setTypedArray(typedArray: TypedArray) {
        tvCurDate.text = typedArray.getText(R.styleable.CustomReservation_curDate)
        tvOpposite.text = typedArray.getText(R.styleable.CustomReservation_opposite)
        tvName.text = typedArray.getText(R.styleable.CustomReservation_rName)
        tvPhoneNumber.text = typedArray.getText(R.styleable.CustomReservation_phonNumber)
        tvReservationDate.text = typedArray.getText(R.styleable.CustomReservation_resDate)
        tvTime.text = typedArray.getText(R.styleable.CustomReservation_startTime)
        tvPlace.text = typedArray.getText(R.styleable.CustomReservation_rLocation)
        tvCategory.text = typedArray.getText(R.styleable.CustomReservation_rCategory)
        tvCost.text = typedArray.getText(R.styleable.CustomReservation_cost)
        typedArray.recycle()
    }

    fun setAttrs(data: CustomReservationDomainDto) {
        tvCurDate.text = data.curDate
        tvOpposite.text = data.opposite
        tvName.text = data.name
        tvPhoneNumber.text = data.phoneNumber
        tvReservationDate.text = data.resDate
        tvTime.text = data.startTime
        tvPlace.text = data.location
        tvCategory.text = data.category
        tvCost.text = data.cost

        invalidate()
        requestLayout()
    }
}