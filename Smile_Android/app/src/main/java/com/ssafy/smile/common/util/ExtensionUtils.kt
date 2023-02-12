package com.ssafy.smile.common.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.ssafy.smile.R
import java.text.DecimalFormat


fun View.setOnSingleClickListener(onSingleClick: (View) -> Unit) {
    val singleClickListener = EventUtils.OnSingleClickListener { onSingleClick(it) }
    setOnClickListener(singleClickListener)
}


fun Fragment.showKeyboard(view:View){
    val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, 0)
}

fun Fragment.hideKeyboard(view: View) {
    val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun EditText.getString() = this.text.toString().trim()
fun String?.getString() = this.toString().trim()
fun Context.getString(drawableId : Int, msg:String) = String.format(this.getString(drawableId), msg)

fun Int.makeComma():String{
    val comma = DecimalFormat("#,###")
    return comma.format(this)
}
