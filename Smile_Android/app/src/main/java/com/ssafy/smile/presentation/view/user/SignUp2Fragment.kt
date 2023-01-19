package com.ssafy.smile.presentation.view.user

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ssafy.smile.MainActivity
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.util.SharedPreferencesUtil
import com.ssafy.smile.databinding.FragmentSignUp2Binding
import com.ssafy.smile.domain.model.SignUpDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.UserViewModel

class SignUp2Fragment : BaseFragment<FragmentSignUp2Binding>(FragmentSignUp2Binding::bind, R.layout.fragment_sign_up2) {

    private val userViewModel by activityViewModels<UserViewModel>()
    private val args: SignUp1FragmentArgs by navArgs()

    var nameInput = false
    var phoneInput = false
    var phoneCertInput = false

    private var phoneCertCheck = false

    var phoneCertNumber: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setEvent()
    }

    override fun initView() {
        (activity as MainActivity).setToolBar(isUsed = true, isBackUsed = true, title = "회원가입")
    }

    override fun setEvent() {
        binding.apply {
            etChangedListener(etName, "name")
            etChangedListener(etPhone, "phone")
            etChangedListener(etCertification, "certification")

            btnCertification.setOnClickListener {
                val phoneNumber = etPhone.text.toString()
                if(phoneNumber.isNotEmpty()) {
                    userViewModel.checkPhoneNumber(phoneNumber)
                }
            }

            btnCertificationOk.setOnClickListener {
                if(phoneCertNumber != null) {
                    phoneCertCheck = if (phoneCertNumber == etCertification.text.toString().toInt()) {
                        setPhoneNumberCheckVisibility(View.VISIBLE, View.GONE)
                        true
                    } else {
                        setPhoneNumberCheckVisibility(View.GONE, View.VISIBLE)
                        false
                    }
                } else {
                    showToast(requireContext(), "인증 번호를 입력해주세요", Types.ToastType.WARNING)
                }
            }

            btnJoin.setOnClickListener {
                if(isValid()) {
                    val signUpInfo = getSignUpInfo()
                    userViewModel.signUp(signUpInfo)
                } else {
                    showToast(requireContext(), "모든 값을 확인해주세요", Types.ToastType.WARNING)
                }
            }
        }
        signUpResponseObserver()
        checkPhoneNumberResponseObserver()
    }

    private fun checkPhoneNumberResponseObserver() {
        userViewModel.phoneNumberCheckResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Success -> {
                    phoneCertNumber = it.data
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    showToast(requireContext(), "휴대전화 인증 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                    setPhoneNumberCheckVisibility(View.GONE, View.GONE)
                    phoneCertCheck = false
                }
                is NetworkUtils.NetworkResponse.Loading -> {
                    setPhoneNumberCheckVisibility(View.GONE, View.GONE)
                    phoneCertCheck = false
                }
            }
        }
    }

    private fun setPhoneNumberCheckVisibility(ok: Int, no: Int) {
        binding.apply {
            groupPhoneOk.visibility = ok
            groupPhoneNo.visibility = no
        }
    }

    private fun signUpResponseObserver() {
        userViewModel.signUpResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Success -> {
                    SharedPreferencesUtil(requireContext()).putAuthToken(it.data.token)
                    SharedPreferencesUtil(requireContext()).putRole(it.data.role)
                    findNavController().navigate(R.id.action_signUp2Fragment_to_mainFragment)
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    showToast(requireContext(), "회원 가입 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
                is NetworkUtils.NetworkResponse.Loading -> {}
            }
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
        return phoneCertCheck
    }

    private fun isAllInput(): Boolean {
        return nameInput && phoneInput && phoneCertInput
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

    private fun getSignUpInfo(): SignUpDomainDto {
        binding.apply {
            return SignUpDomainDto(args.id, args.password, etName.text.toString(), etPhone.text.toString())
        }
    }
}