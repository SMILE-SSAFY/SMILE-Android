package com.ssafy.smile.presentation.view.user

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.MainActivity
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentSignUp1Binding
import com.ssafy.smile.presentation.base.BaseFragment


class SignUp1Fragment : BaseFragment<FragmentSignUp1Binding>(FragmentSignUp1Binding::bind, R.layout.fragment_sign_up1) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setToolBar(true, true, "회원가입")

        binding.apply {
            btnNext.setOnClickListener {
                findNavController().navigate(R.id.action_signUp1Fragment_to_signUp2Fragment)
            }
        }
    }
}