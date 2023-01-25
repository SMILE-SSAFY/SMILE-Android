package com.ssafy.smile.common.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.ssafy.smile.common.util.setOnSingleClickListener
import com.ssafy.smile.databinding.DialogCommonBinding
import com.ssafy.smile.domain.model.DialogBody
import kotlin.system.exitProcess


class CommonDialog (context: Context,
                    private val content: DialogBody,
                    private var onCustomListener : (()->Unit)? = null,
                    private var onCancelListener : (()->Unit)? = null) : Dialog(context) {

    private val binding: DialogCommonBinding = DialogCommonBinding.inflate(layoutInflater)

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
        binding.run {
            tvContent.text = content.content
            btnCustom.text = content.customBtn
            btnCancel.text = content.closeBtn
            btnCustom.setOnSingleClickListener {
                onCustomListener?.invoke()
                dismiss()
            }
            btnCancel.setOnSingleClickListener {
                onCancelListener?.invoke()
                dismiss()
            }
        }
    }

    override fun show() {
        if(!this.isShowing) super.show()
    }

}