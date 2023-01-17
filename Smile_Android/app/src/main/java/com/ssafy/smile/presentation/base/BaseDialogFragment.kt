package com.ssafy.smile.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.ssafy.smile.common.view.CommonDialog
import com.ssafy.smile.common.view.LoadingDialog

abstract class BaseDialogFragment<B : ViewBinding>(private val bindingInflater: (layoutInflater: LayoutInflater) -> B)
    : DialogFragment(), BaseViewImpl  {
    private var _binding: B? = null
    val binding get() = _binding?: throw IllegalStateException("binding fail")

    override lateinit var mLoadingDialog: LoadingDialog


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater.invoke(inflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setEvent()
    }

    abstract fun initView()
    abstract fun setEvent()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}
