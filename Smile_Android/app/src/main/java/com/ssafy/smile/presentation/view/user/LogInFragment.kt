package com.ssafy.smile.presentation.view.user

import androidx.navigation.fragment.findNavController
import com.ssafy.smile.MainActivity
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentLogInBinding
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment

class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::bind, R.layout.fragment_log_in) {

    override fun initView() {
        (activity as MainActivity).setToolBar(false, false, null)
    }

    override fun setEvent() {
        binding.apply {
            btnLogin.setOnClickListener {
                if (checkValid()) {
                    //TODO : 자동 로그인 처리
                    //TODO : 로그인 서버통신 구현
                    findNavController().navigate(R.id.action_logInFragment_to_mainFragment)
                }
            }

            btnSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_logInFragment_to_signUp1Fragment)
            }

            btnSnsLogin.setOnClickListener {
                //TODO : SNS 로그인 구현
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
}