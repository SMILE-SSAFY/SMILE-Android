package com.ssafy.smile.common.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

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