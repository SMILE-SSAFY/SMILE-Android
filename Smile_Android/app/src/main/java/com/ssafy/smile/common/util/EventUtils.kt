package com.ssafy.smile.common.util

import android.view.View

object EventUtils {

    class OnSingleClickListener(private val onSingleClick: (View) -> Unit) : View.OnClickListener {

        companion object { private const val CLICK_INTERVAL = 500 }

        private var lastClickedTime: Long = 0L

        override fun onClick(v: View?) {
            if (isSafe() && v != null) {
                lastClickedTime = System.currentTimeMillis()
                onSingleClick(v)
            }
        }

        private fun isSafe(): Boolean {
            return System.currentTimeMillis() - lastClickedTime > CLICK_INTERVAL
        }
    }
}