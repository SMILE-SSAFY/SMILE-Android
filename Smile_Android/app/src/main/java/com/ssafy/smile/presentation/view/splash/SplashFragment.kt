package com.ssafy.smile.presentation.view.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private lateinit var viewbinding : FragmentSplashBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewbinding = FragmentSplashBinding.inflate(inflater, container, false)
        return viewbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        }, 1000)
    }
}