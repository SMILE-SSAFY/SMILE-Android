package com.ssafy.smile.presentation.view.splash

import android.view.View
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentSplashBinding
import com.ssafy.smile.presentation.base.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::bind, R.layout.fragment_splash) {

    override fun initView() { }

    override fun setEvent() {
        val scope = CoroutineScope(Dispatchers.Main)

        scope.launch {
            delay(3000)

            val splashAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.animation_splash)
            binding.apply {
                ivLogo.visibility = View.VISIBLE
                binding.ivLogo.startAnimation(splashAnim)
            }

            delay(1200)
            findNavController().navigate(R.id.action_splashFragment_to_signUp1Fragment)
        }
    }
}