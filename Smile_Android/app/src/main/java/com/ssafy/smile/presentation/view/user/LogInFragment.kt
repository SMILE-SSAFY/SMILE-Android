package com.ssafy.smile.presentation.view.user

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.MainActivity
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.util.SharedPreferencesUtil
import com.ssafy.smile.databinding.FragmentLogInBinding
import com.ssafy.smile.domain.model.LoginDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.UserViewModel

private const val TAG = "LogInFragment_스마일"
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::bind, R.layout.fragment_log_in) {

    private val userViewModel by activityViewModels<UserViewModel>()

    override fun initView() {
        (activity as MainActivity).setToolBar(isUsed = false, isBackUsed = false, title = null)
    }

    override fun setEvent() {
        binding.apply {
            btnLogin.setOnClickListener {
                if (checkValid()) {
                    val loginInfo = getLoginInfo()

                    if (cbAutoLogin.isChecked) {
                        //TODO : 자동 로그인 구현
                    }

                    userViewModel.login(loginInfo)
                }
            }

            btnSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_logInFragment_to_signUp1Fragment)
            }

            btnSnsLogin.setOnClickListener {
                //TODO : SNS 로그인 구현
            }
        }
        loginResponseObserver()
    }

    private fun loginResponseObserver() {
        userViewModel.loginResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Success -> {
                    SharedPreferencesUtil(requireContext()).putAuthToken(it.data.token)
                    SharedPreferencesUtil(requireContext()).putRole(it.data.role)
                    findNavController().navigate(R.id.action_logInFragment_to_mainFragment)
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    if (it.errorCode == 404) {
                        showToast(requireContext(), "존재하지 않는 회원입니다. 다시 로그인해주세요.", Types.ToastType.WARNING)
                    } else {
                        showToast(requireContext(), "로그인 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                    }
                }
                is NetworkUtils.NetworkResponse.Loading -> {}
            }
        }
    }

    private fun checkValid(): Boolean {
        binding.apply {
            return if (etId.text.toString().isEmpty()) {
                showToast(requireContext(), "아이디를 입력해주세요", Types.ToastType.ERROR)
                false
            } else if (etPassword.text.toString().isEmpty()) {
                showToast(requireContext(), "비밀번호를 입력해주세요", Types.ToastType.ERROR)
                false
            } else {
                true
            }
        }
    }

    private fun getLoginInfo(): LoginDomainDto {
        binding.apply {
            return LoginDomainDto(etId.text.toString(), etPassword.text.toString())
        }
    }
}