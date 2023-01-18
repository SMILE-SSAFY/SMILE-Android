package com.ssafy.smile.presentation.view.user

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import com.ssafy.smile.MainActivity
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.databinding.FragmentSignUp2Binding
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.UserViewModel

class SignUp2Fragment : BaseFragment<FragmentSignUp2Binding>(FragmentSignUp2Binding::bind, R.layout.fragment_sign_up2) {

    private val userViewModel by activityViewModels<UserViewModel>()

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
                userViewModel.checkEmail(etNickname.text.toString())
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

        nicknameCheckResponseObserver()
    }

    private fun nicknameCheckResponseObserver() {
        userViewModel.nicknameCheckResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Success -> {
                    nicknameDoubleCheck = if (it.data) {
                        setNicknameCheckVisibility(View.VISIBLE, View.GONE)
                        true
                    } else {
                        setNicknameCheckVisibility(View.GONE, View.VISIBLE)
                        false
                    }
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    setNicknameCheckVisibility(View.GONE, View.GONE)
                    nicknameDoubleCheck = false

                    showToast(requireContext(), "닉네임 중복 체크 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
                is NetworkUtils.NetworkResponse.Loading -> {
                    setNicknameCheckVisibility(View.GONE, View.GONE)
                    nicknameDoubleCheck = false
                }
            }
        }
    }

    private fun setNicknameCheckVisibility(ok: Int, no: Int) {
        binding.apply {
            groupNicknameOk.visibility = ok
            groupNicknameNo.visibility = no
        }
    }

    private fun etChangedListener(editText: EditText, type: String) {
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