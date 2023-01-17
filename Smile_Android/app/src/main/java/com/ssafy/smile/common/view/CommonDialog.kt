package com.ssafy.smile.common.view

import com.ssafy.smile.common.util.setOnSingleClickListener
import com.ssafy.smile.databinding.DialogCustomBinding
import com.ssafy.smile.domain.model.DialogBody
import com.ssafy.smile.presentation.base.BaseDialogFragment



// [활용] 생성자 = CommonDialog(DialogBody(), {}, {})
class CommonDialog (private val content: DialogBody,
                    private var onCancelListener : (()->Unit)? = null,
                    private var onCustomListener : (()->Unit)? = null)
    : BaseDialogFragment<DialogCustomBinding>(DialogCustomBinding::inflate) {

    override fun initView() {
        content.run {
            binding.tvContent.text = content
        }
    }

    override fun setEvent() {
        binding.apply {
            btnCancel.setOnSingleClickListener {
                onCancelListener?.invoke()
                dismiss()
            }
            btnCustom.setOnSingleClickListener {
                onCustomListener?.invoke()
                dismiss()
            }
        }
    }

}