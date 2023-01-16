package com.ssafy.smile.presentation.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.ssafy.smile.common.view.CustomDialog
import es.dmoral.toasty.Toasty

// Fragment의 기본을 작성, 뷰 바인딩 활용
abstract class BaseFragment<B : ViewBinding>(private val bind: (View) -> B, @LayoutRes layoutResId: Int) : Fragment(layoutResId) {
    private var _binding: B? = null
    val binding get() = _binding?: throw IllegalStateException("binding fail")

    lateinit var mLoadingDialog: CustomDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun showToast(message: String, type : ToastType?=null, iconEnable : Boolean=true) {
        when (type){
            ToastType.CUSTOM -> Toasty.warning(requireContext(), message, Toast.LENGTH_SHORT).show()
            ToastType.INFO -> Toasty.info(requireContext(), message, Toast.LENGTH_SHORT, iconEnable).show()
            ToastType.ERROR -> Toasty.error(requireContext(), message, Toast.LENGTH_SHORT, iconEnable).show()
            ToastType.SUCCESS -> Toasty.success(requireContext(), message, Toast.LENGTH_SHORT, iconEnable).show()
            ToastType.WARNING -> Toasty.warning(requireContext(), message, Toast.LENGTH_SHORT, iconEnable).show()
            else -> Toasty.normal(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    // TODO : customDialog - 에러 처리 필요

    private fun showDialog(dialog: Dialog, lifecycleOwner: LifecycleOwner?, cancelable: Boolean = true, dismissHandler: (() -> Unit)? = null) {
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

    fun showLoadingDialog(context: Context) {
        mLoadingDialog = CustomDialog(context)
        mLoadingDialog.show()
    }

    fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }

    // TODO : Dialog 위에 꺼 정리

    enum class ToastType { ERROR, SUCCESS, INFO, WARNING, BASIC, CUSTOM}
}