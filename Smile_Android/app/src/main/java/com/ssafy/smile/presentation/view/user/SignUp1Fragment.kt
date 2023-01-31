package com.ssafy.smile.presentation.view.user

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.databinding.FragmentSignUp1Binding
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.user.UserViewModel
import java.util.regex.Pattern


private const val TAG = "SignUp1Fragment_스마일"
class SignUp1Fragment : BaseFragment<FragmentSignUp1Binding>(FragmentSignUp1Binding::bind, R.layout.fragment_sign_up1) {

    private val userViewModel: UserViewModel by navGraphViewModels(R.id.signUpGraph)

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
        initToolbar()
        setObserver()
    }

    private fun setObserver() {
        emailCheckResponseObserver()
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("회원가입", true) { moveToPopUpSelf() }
    }

    private fun moveToPopUpSelf() = findNavController().navigate(R.id.action_signUp1Fragment_pop)

    override fun setEvent() {
        binding.apply {
            etChangedListener(etId, "id")
            etChangedListener(etPassword, "pwd")
            etChangedListener(etPasswordCheck, "pwdChk")

            btnDoubleCheck.setOnClickListener {
                userViewModel.checkEmail(etId.text.toString())
            }

            btnPasswordCheck.setOnClickListener {
                pwdDoubleCheck = if (etPassword.text.toString() == etPasswordCheck.text.toString()) {
                    setPasswordCheckVisibility(View.VISIBLE, View.GONE)
                    true
                } else {
                    setPasswordCheckVisibility(View.GONE, View.VISIBLE)
                    false
                }
            }

            btnNext.setOnClickListener {
                if(isValid()) {
                    val action = SignUp1FragmentDirections.actionSignUp1FragmentToSignUp2Fragment(etId.text.toString(), etPassword.text.toString())
                    findNavController().navigate(action)
                } else {
                    showToast(requireContext(), "모든 값을 확인해주세요", Types.ToastType.WARNING)
                }
            }
        }
    }

    private fun setPasswordCheckVisibility(ok: Int, no: Int) {
        binding.apply {
            groupPasswordOk.visibility = ok
            groupPasswordNo.visibility = no
        }
    }

    private fun emailCheckResponseObserver() {
        userViewModel.emailCheckResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Loading -> {
                    //TODO : 로딩 다이얼로그 고치기
//                    showLoadingDialog(requireContext())
                    setIdCheckVisibility(View.GONE, View.GONE)
                    idDoubleCheck = false
                }
                is NetworkUtils.NetworkResponse.Success -> {
                    //TODO : 로딩 다이얼로그 고치기
//                    dismissLoadingDialog()
                    idDoubleCheck = if (it.data == "OK") {
                        setIdCheckVisibility(View.VISIBLE, View.GONE)
                        true
                    } else {
                        false
                    }
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    //TODO : 로딩 다이얼로그 고치기
//                    dismissLoadingDialog()
                    idDoubleCheck = false
                    if (it.errorCode == 400) {
                        setIdCheckVisibility(View.GONE, View.VISIBLE)
                    } else {
                        setIdCheckVisibility(View.GONE, View.GONE)
                        showToast(requireContext(), "이메일 중복 체크 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                    }
                }
            }
        }
    }

    private fun setIdCheckVisibility(ok: Int, no: Int) {
        binding.apply {
            groupIdOk.visibility = ok
            groupIdNo.visibility = no
        }
    }

    private fun etChangedListener(editText: EditText, type: String) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (type == "id") {
                    val email = binding.etId.text.toString()
                    if (!checkEmailRule(email)) {
                        binding.etId.error = "올바른 이메일 주소를 입력해주세요"
                    }
                } else if (type == "pwd") {
                    val password = binding.etPassword.text.toString()
                    if (!checkPasswordRule(password)) {
                        binding.etPassword.error = "비밀번호 규칙에 맞게 입력해주세요"
                    }
                }
            }
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

    private fun checkEmailRule(email: String): Boolean {
        val rule = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val pattern = Pattern.compile(rule)

        return pattern.matcher(email).find()
    }

    private fun checkPasswordRule(password: String): Boolean {
        val rule = "^.{8,20}$"
        val pattern = Pattern.compile(rule)

        return pattern.matcher(password).find()
    }
}