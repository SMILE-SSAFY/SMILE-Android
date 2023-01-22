package com.ssafy.smile.presentation.base

import android.app.Dialog
import android.content.Context
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import com.ssafy.smile.domain.model.Types

interface BaseView {
    fun Toolbar.initToolbar(toolbarTitle:String, isBackAvailable:Boolean?=false, backAction : (() -> Unit)?=null )
    fun showLoadingDialog(context: Context){}
    fun dismissLoadingDialog(){}
    fun showDialog(dialog: Dialog, lifecycleOwner: LifecycleOwner?, cancelable: Boolean = true, dismissHandler: (() -> Unit)? = null){}
    fun showToast(context: Context, message: String, type : Types.ToastType?=null, iconEnable : Boolean=true)
}