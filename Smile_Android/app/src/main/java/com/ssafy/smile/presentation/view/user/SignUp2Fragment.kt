package com.ssafy.smile.presentation.view.user

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.util.SharedPreferencesUtil
import com.ssafy.smile.common.util.setOnSingleClickListener
import com.ssafy.smile.databinding.FragmentSignUp2Binding
import com.ssafy.smile.domain.model.SignUpDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.user.UserViewModel

private const val TAG = "SignUp2Fragment_스마일"
class SignUp2Fragment : BaseFragment<FragmentSignUp2Binding>(FragmentSignUp2Binding::bind, R.layout.fragment_sign_up2) {

    private val userViewModel: UserViewModel by navGraphViewModels(R.id.signUpGraph)
    private val args: SignUp2FragmentArgs by navArgs()

    var nameInput = false
    var phoneInput = false
    var phoneCertInput = false

    private var phoneCertCheck = false

    var phoneCertNumber: Int? = null

    override fun initView() {
        initToolbar()
        setObserver()
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("회원가입", true) { moveToPopUpSelf() }
    }

    private fun moveToPopUpSelf() = findNavController().navigate(R.id.action_signUp1Fragment_pop)

    private fun setObserver() {
        signUpResponseObserver()
        checkPhoneNumberResponseObserver()
    }

    override fun setEvent() {
        binding.apply {
            etChangedListener(etName, "name")
            etChangedListener(etPhone, "phone")
            etChangedListener(etCertification, "certification")

//            btnCertification.setOnClickListener {
//                val phoneNumber = etPhone.text.toString()
//                if(phoneNumber.isNotEmpty()) {
//                    startTimer()
//                    userViewModel.checkPhoneNumber(phoneNumber)
//                }
//            }

            btnCertificationOk.setOnClickListener {
                phoneCertCheck = true
//                if(phoneCertNumber != null) {
//                    phoneCertCheck = if (phoneCertNumber == etCertification.text.toString().toInt()) {
//                        setPhoneNumberCheckVisibility(View.VISIBLE, View.GONE)
//                        true
//                    } else {
//                        setPhoneNumberCheckVisibility(View.GONE, View.VISIBLE)
//                        false
//                    }
//                } else {
//                    showToast(requireContext(), "인증 번호를 입력해주세요", Types.ToastType.WARNING)
//                }
            }

            btnJoin.setOnSingleClickListener {
                if(isValid()) {
                    val signUpInfo = getSignUpInfo()
                    userViewModel.signUp(signUpInfo)
                } else {
                    showToast(requireContext(), "모든 값을 확인해주세요", Types.ToastType.WARNING)
                }
            }
        }
    }

    private fun startTimer() {
        binding.apply {
            groupTimer.visibility = View.VISIBLE

            object : CountDownTimer(180000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondsUntilFinished = millisUntilFinished / 1000
                    tvTimer.text = "${secondsUntilFinished / 60}:${secondsUntilFinished % 60}"
                }
                override fun onFinish() {
                    tvTimer.visibility = View.GONE
                    tvTimeText.text = "인증 시간이 만료되었습니다. 다시 시도해주세요"
                }
            }.start()
        }
    }

    private fun checkPhoneNumberResponseObserver() {
        userViewModel.phoneNumberCheckResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()
                    phoneCertNumber = it.data
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    showToast(requireContext(), "휴대전화 인증 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                    setPhoneNumberCheckVisibility(View.GONE, View.GONE)
                    phoneCertCheck = false
                }
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
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
                    dismissLoadingDialog()
                    SharedPreferencesUtil(requireContext()).putAuthToken("Bearer ${it.data.token}")
                    SharedPreferencesUtil(requireContext()).putAuthTime(System.currentTimeMillis())
                    SharedPreferencesUtil(requireContext()).putRole(it.data.role)
                    SharedPreferencesUtil(requireContext()).putUserId(it.data.userId)
                    if(findNavController().currentDestination?.id == R.id.signUp2Fragment) {
                        findNavController().navigate(R.id.action_signUp2Fragment_to_mainFragment)
                    }
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    Log.d(TAG, "signUpResponseObserver: ${it.errorCode}")
                    showToast(requireContext(), "회원 가입 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
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