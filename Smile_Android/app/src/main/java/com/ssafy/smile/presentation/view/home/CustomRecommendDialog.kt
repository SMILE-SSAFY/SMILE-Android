package com.ssafy.smile.presentation.view.home

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.ssafy.smile.common.util.setOnSingleClickListener
import com.ssafy.smile.databinding.DialogCustomRecommendBinding
import kotlin.system.exitProcess
import com.ssafy.smile.presentation.view.MainFragmentDirections

class CustomRecommendDialog(context: Context): Dialog(context) {

    interface OnButtonClickListener {
        fun onOkButtonClick()
    }
    lateinit var onButtonClickListener : OnButtonClickListener
    fun setButtonClickListener(onButtonClickListener: OnButtonClickListener){ this.onButtonClickListener = onButtonClickListener }

    private val binding: DialogCustomRecommendBinding = DialogCustomRecommendBinding.inflate(layoutInflater)

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        window?.run {
            setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 50))
            attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
            attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        } ?: exitProcess(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {

            btnOk.setOnSingleClickListener {
                onButtonClickListener.onOkButtonClick()
                dismiss()
            }
            btnCancel.setOnSingleClickListener {
                dismiss()
            }
        }
    }

    override fun show() {
        if(!this.isShowing) super.show()
    }
}