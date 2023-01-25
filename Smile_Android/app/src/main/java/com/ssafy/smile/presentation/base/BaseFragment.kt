package com.ssafy.smile.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ssafy.smile.MainActivity
import com.ssafy.smile.common.view.LoadingDialog


abstract class BaseFragment<B : ViewBinding>(private val bind: (View) -> B, @LayoutRes layoutResId: Int)
    : Fragment(layoutResId), BaseViewImpl {

    override lateinit var mLoadingDialog: LoadingDialog
    lateinit var mainActivity : MainActivity

    private var _binding: B? = null
    val binding get() = _binding?: throw IllegalStateException("binding fail")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
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