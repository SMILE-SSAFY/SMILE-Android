package com.ssafy.smile.presentation.view.portfolio

import android.content.Context
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class CantReservationDecorator(context: Context, currentDay: CalendarDay) : DayViewDecorator {

    private var myDay = currentDay

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day == myDay
    }

    override fun decorate(view: DayViewFacade) {
        view.setDaysDisabled(true)
    }
}