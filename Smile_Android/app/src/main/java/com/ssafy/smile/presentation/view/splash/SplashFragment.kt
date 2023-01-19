package com.ssafy.smile.presentation.view.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentSplashBinding
import com.ssafy.smile.presentation.base.BaseFragment

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::bind, R.id.splashFragment) {
    private lateinit var viewbinding : FragmentSplashBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewbinding = FragmentSplashBinding.inflate(inflater, container, false)
        return viewbinding.root
    }

    override fun initView() { }

    override fun setEvent() {
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_logInFragment)
        }, 1000)
    }
}