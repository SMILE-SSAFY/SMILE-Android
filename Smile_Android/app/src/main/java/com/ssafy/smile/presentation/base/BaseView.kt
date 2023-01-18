package com.ssafy.smile.presentation.base

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LifecycleOwner
import com.ssafy.smile.domain.model.Types
import es.dmoral.toasty.Toasty

interface BaseView {
    fun showLoadingDialog(context: Context){}
    fun dismissLoadingDialog(){}
    fun showDialog(dialog: Dialog, lifecycleOwner: LifecycleOwner?, cancelable: Boolean = true, dismissHandler: (() -> Unit)? = null){}
    fun showToast(context: Context, message: String, type : Types.ToastType?=null, iconEnable : Boolean=true)
}