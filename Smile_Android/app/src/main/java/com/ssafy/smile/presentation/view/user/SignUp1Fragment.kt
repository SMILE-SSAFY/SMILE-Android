package com.ssafy.smile.presentation.view.user

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.MainActivity
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentSignUp1Binding
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment


class SignUp1Fragment : BaseFragment<FragmentSignUp1Binding>(FragmentSignUp1Binding::bind, R.layout.fragment_sign_up1) {

    var idInput = false
    var pwdInput = false
    var pwdDoubleInput = false

    private var idDoubleCheck = false
    private var pwdDoubleCheck = false

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
            etChangedListener(etId, "id")
            etChangedListener(etPassword, "pwd")
            etChangedListener(etPasswordCheck, "pwdChk")

            btnDoubleCheck.setOnClickListener {
                idDoubleCheck = true
            }

            btnPasswordCheck.setOnClickListener {
                if (etPassword.text.toString() == etPasswordCheck.text.toString()) {
                    showToast(requireContext(), "비밀번호가 확인되었습니다", Types.ToastType.SUCCESS)
                    pwdDoubleCheck = true
                } else {
                    etPasswordCheck.error = "비밀번호가 일치하지 않습니다"
                }
            }

            btnNext.setOnClickListener {
                if(isValid()) {
                    findNavController().navigate(R.id.action_signUp1Fragment_to_signUp2Fragment)
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
                        "id" -> idInput = true
                        "pwd" -> pwdInput = true
                        "pwdChk" -> pwdDoubleInput = true
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
        return idDoubleCheck && pwdDoubleCheck
    }

    private fun isAllInput(): Boolean {
        return idInput && pwdInput && pwdDoubleInput
    }

    private fun setButtonEnable() {
        binding.btnNext.apply {
            isClickable = true
            setBackgroundResource(R.drawable.rectangle_blue400_radius_8)
        }
    }

    private fun setButtonDisable() {
        binding.btnNext.apply {
            isClickable = false
            setBackgroundResource(R.drawable.rectangle_gray400_radius_8)
        }
    }
}