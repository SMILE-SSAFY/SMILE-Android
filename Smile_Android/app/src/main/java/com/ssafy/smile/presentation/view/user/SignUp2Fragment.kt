package com.ssafy.smile.presentation.view.user

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.ssafy.smile.MainActivity
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentSignUp2Binding
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment

class SignUp2Fragment : BaseFragment<FragmentSignUp2Binding>(FragmentSignUp2Binding::bind, R.layout.fragment_sign_up2) {

    var nameInput = false
    var nicknameInput = false
    var phoneInput = false
    var phoneCertInput = false

    private var nicknameDoubleCheck = false
    private var phoneCertCheck = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setEvent()
    }

    override fun initView() {
        (activity as MainActivity).setToolBar(true, true, "회원가입")
    }

    override fun setEvent() {
        binding.apply {
            etChangedListener(etName, "name")
            etChangedListener(etNickname, "nickname")
            etChangedListener(etPhone, "phone")
            etChangedListener(etCertification, "certification")

            btnDoubleCheck.setOnClickListener {
                nicknameDoubleCheck = true
            }

            btnCertificationOk.setOnClickListener {
                phoneCertCheck = true
            }

            btnJoin.setOnClickListener {
                if(isValid()) {
                    // TODO : 회원가입 서버 통신 하고 로그인
                } else {
                    showToast(requireContext(), "모든 값을 확인해주세요", Types.ToastType.WARNING)
                }
            }
        }
    }

    fun etChangedListener(editText: EditText, type: String) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.isNotEmpty()) {
                    when(type) {
                        "name" -> nameInput = true
                        "nickname" -> nicknameInput = true
                        "phone" -> phoneInput = true
                        "certification" -> phoneCertInput = true
                        else -> {}
                    }

                    if(isAllInput()) {
                        setButtonEnable()
                    }
                } else {
                    setButtonDisable()
                }
            }
        })
    }

    private fun isValid(): Boolean {
        return nicknameDoubleCheck && phoneCertCheck
    }

    private fun isAllInput(): Boolean {
        return nameInput && nicknameInput && phoneInput && phoneCertInput
    }

    private fun setButtonEnable() {
        binding.btnJoin.apply {
            isClickable = true
            setBackgroundResource(R.drawable.rectangle_blue400_radius_8)
        }
    }

    private fun setButtonDisable() {
        binding.btnJoin.apply {
            isClickable = false
            setBackgroundResource(R.drawable.rectangle_gray400_radius_8)
        }
    }
}