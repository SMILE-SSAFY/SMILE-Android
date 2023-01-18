package com.ssafy.smile.presentation.base

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.ssafy.smile.common.view.LoadingDialog
import com.ssafy.smile.domain.model.Types
import es.dmoral.toasty.Toasty

interface BaseViewImpl : BaseView {

    var mLoadingDialog: LoadingDialog

    override fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }
    override fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }
    override fun showDialog(dialog: Dialog, lifecycleOwner: LifecycleOwner?, cancelable: Boolean, dismissHandler: (() -> Unit)?) {
        val targetEvent = if (cancelable) Lifecycle.Event.ON_STOP else Lifecycle.Event.ON_DESTROY
        val observer = LifecycleEventObserver { _: LifecycleOwner, event: Lifecycle.Event ->
            if (event == targetEvent && dialog.isShowing) {
                dialog.dismiss()
                dismissHandler?.invoke()
            }
        }
        dialog.show()
        lifecycleOwner?.lifecycle?.addObserver(observer)
        dialog.setOnDismissListener { lifecycleOwner?.lifecycle?.removeObserver(observer) }
    }

    override fun showToast(context: Context, message: String, type : Types.ToastType?, iconEnable : Boolean) {
        when (type){
            Types.ToastType.CUSTOM -> Toasty.warning(context, message, Toast.LENGTH_SHORT).show()
            Types.ToastType.INFO -> Toasty.info(context, message, Toast.LENGTH_SHORT, iconEnable).show()
            Types.ToastType.ERROR -> Toasty.error(context, message, Toast.LENGTH_SHORT, iconEnable).show()
            Types.ToastType.SUCCESS -> Toasty.success(context, message, Toast.LENGTH_SHORT, iconEnable).show()
            Types.ToastType.WARNING -> Toasty.warning(context, message, Toast.LENGTH_SHORT, iconEnable).show()
            else -> Toasty.normal(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}