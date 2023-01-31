package com.ssafy.smile.presentation.base


import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.smile.R
import com.ssafy.smile.common.view.LoadingDialog


abstract class BaseBottomSheetDialogFragment<B : ViewBinding>(private val bindingInflater: (layoutInflater:LayoutInflater) -> B)
    : BottomSheetDialogFragment(), BaseViewImpl {

    override lateinit var mLoadingDialog: LoadingDialog

    private var _binding: B? = null
    val binding get() = _binding?: throw IllegalStateException("binding fail")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater.invoke(inflater)
        return _binding?.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            setupRatio(bottomSheet!!)
        }
        return dialog
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

    fun setupRatio(view : View, ratio:Int=35){
        val layoutParams = view.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight(ratio)
        view.layoutParams = layoutParams
    }

    private fun getBottomSheetDialogDefaultHeight(ratio:Int): Int { return getWindowHeight() * ratio / 100 }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

}