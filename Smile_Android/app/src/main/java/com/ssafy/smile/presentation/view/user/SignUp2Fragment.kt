package com.ssafy.smile.presentation.view.user

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.MainActivity
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentSignUp2Binding
import com.ssafy.smile.presentation.base.BaseFragment

class SignUp2Fragment : BaseFragment<FragmentSignUp2Binding>(FragmentSignUp2Binding::bind, R.layout.fragment_sign_up2) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setToolBar(true, true, "회원가입")

        binding.apply {
            btnJoin.setOnClickListener {
                // TODO : 바로 로그인
            }
        }
    }
}