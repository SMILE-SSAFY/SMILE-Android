package com.ssafy.smile.common.util

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import java.text.DecimalFormat
import java.text.NumberFormat


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

    class OnCurrentTextWatchListener(private val editText: EditText) : TextWatcher {
        private var current: String = ""
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            if (charSequence.toString() != current) {
                editText.removeTextChangedListener(this)

                val parsed = charSequence.replace("""[,원]""".toRegex(), "").trim().toInt()
                val formatted = makeComma(parsed)
                editText.setText(formatted)
                editText.setSelection(formatted.length)
                editText.addTextChangedListener(this)
            }
        }
        override fun afterTextChanged(editable: Editable) {}
        private fun makeComma(num:Int):String{
            val comma = DecimalFormat("#,###")
            return "${comma.format(num)}원"
        }
    }

}