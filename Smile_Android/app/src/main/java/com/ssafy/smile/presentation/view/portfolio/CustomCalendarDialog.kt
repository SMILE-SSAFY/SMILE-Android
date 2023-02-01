package com.ssafy.smile.presentation.view.portfolio

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.ssafy.smile.common.util.setOnSingleClickListener
import com.ssafy.smile.databinding.DialogCustomCalendarBinding
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*
import kotlin.system.exitProcess

class CustomCalendarDialog(context: Context, private val unableDate: ArrayList<Date>): Dialog(context) {

    interface OnButtonClickListener {
        fun onOkButtonClick(year: String, date: String)
    }
    lateinit var onButtonClickListener : OnButtonClickListener
    fun setButtonClickListener(onButtonClickListener: OnButtonClickListener){ this.onButtonClickListener = onButtonClickListener }

    private val binding: DialogCustomCalendarBinding = DialogCustomCalendarBinding.inflate(layoutInflater)

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        window?.run {
            setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 50))
            attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
            attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        } ?: exitProcess(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            setCalendarView()

            btnOk.setOnSingleClickListener {
                onButtonClickListener.onOkButtonClick(tvYear.text.toString(), tvReservationDate.text.toString())
                dismiss()
            }
            btnCancel.setOnSingleClickListener {
                dismiss()
            }
        }
    }

    private fun setCalendarView() {
        binding.apply {
            val calendar = Calendar.getInstance()
            setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH))
        }
        setSelectListener()
        setStartEndMonth()
        setStartEndDay()
        setUnableDay(unableDate)
    }

    private fun setSelectListener() {
        binding.apply {
            calendarView.setOnDateChangedListener { _, sDate, _ ->
                btnOk.isEnabled = true
                setDate(sDate.year, sDate.month, sDate.day)
            }
        }
    }

    private fun setDate(iYear: Int, iMonth: Int, iDate: Int) {
        val month = String.format("%02d", iMonth)
        val date = String.format("%02d", iDate)

        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val day = getDayFromDate(dateFormat.parse("${iYear}${month}${date}")!!)

        binding.apply {
            tvYear.text = "${iYear}년"
            tvReservationDate.text = "${month}월 ${date}일 (${day})"
        }
    }

    private fun getDayFromDate(date: Date): String? {
        val calendar = Calendar.getInstance()
        calendar.time = date

        var day: String? = null
        when(calendar.get(Calendar.DAY_OF_WEEK)) {
            1 -> day = "일"
            2 -> day = "월"
            3 -> day = "화"
            4 -> day = "수"
            5 -> day = "목"
            6 -> day = "금"
            7 -> day = "토"
        }
        return day
    }

    private fun setStartEndMonth() {
        val calendar = Calendar.getInstance()

        val thisMonth = CalendarDay.from(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            1
        )

        calendar.add(Calendar.MONTH, 1)
        val nextMonth = CalendarDay.from(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        )

        binding.calendarView.state().edit()
            .setMinimumDate(thisMonth)
            .setMaximumDate(nextMonth)
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()
    }

    private fun setStartEndDay() {
        val calendar = Calendar.getInstance()
        val calList = ArrayList<CalendarDay>()

        for (day in 1 .. calendar.get(Calendar.DAY_OF_MONTH)) {
            calList.add(CalendarDay.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, day))
        }

        calendar.add(Calendar.MONTH, 1)
        for (day in calendar.get(Calendar.DAY_OF_MONTH) until calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            calList.add(CalendarDay.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, day))
        }

        for (calDay in calList){
            binding.calendarView.addDecorators(CantReservationDecorator(context, calDay))
        }
    }

    private fun setUnableDay(unableDate: ArrayList<Date>) {
        val unableList = ArrayList<CalendarDay>()

        unableDate.forEach { date ->
            val localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            unableList.add(CalendarDay.from(localDate.year, localDate.monthValue, localDate.dayOfMonth))
        }

        for (unableDay in unableList){
            binding.calendarView.addDecorators(CantReservationDecorator(context, unableDay))
        }
    }

    override fun show() {
        if(!this.isShowing) super.show()
    }
}