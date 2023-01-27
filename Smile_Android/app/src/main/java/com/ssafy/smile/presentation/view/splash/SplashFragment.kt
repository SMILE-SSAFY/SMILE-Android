package com.ssafy.smile.presentation.view.splash

import android.view.View
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.R
import com.ssafy.smile.common.util.SharedPreferencesUtil
import com.ssafy.smile.databinding.FragmentSplashBinding
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::bind, R.layout.fragment_splash) {

    var isTokenValid = false

    override fun initView() {
        checkToken()
    }

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

            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
//            if (isTokenValid) {
//                findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
//                showToast(requireContext(), "자동 로그인 되었습니다.", Types.ToastType.BASIC)
//            } else {
//                findNavController().navigate(R.id.action_splashFragment_to_logInFragment)
//            }
        }
    }

    private fun checkToken() {
        val token = SharedPreferencesUtil(requireContext()).getAuthToken()

        isTokenValid = if (token != null) {
            val tokenTime = SharedPreferencesUtil(requireContext()).getAuthTime()
            checkTokenTime(tokenTime)

        } else {
            false
        }
    }

    private fun checkTokenTime(tokenTime: Long): Boolean {
        val curTime = System.currentTimeMillis()

        val timeDiff = abs(curTime - tokenTime) / 1000 / 60 / 60 / 24
        return timeDiff <= 30
    }
}