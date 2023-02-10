package com.ssafy.smile.presentation.view.splash

import android.view.View
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkConnection
import com.ssafy.smile.common.util.SharedPreferencesUtil
import com.ssafy.smile.databinding.FragmentSplashBinding
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment
import kotlinx.coroutines.*
import kotlin.math.abs

private const val TAG = "SplashFragment_스마일"
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::bind, R.layout.fragment_splash) {

    var isTokenValid = false
    var isFirstCheck = true

    override fun initView() {
        checkToken()
    }

    override fun setEvent() {
        NetworkConnection(requireContext()).observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.Main).launch {
                if (isFirstCheck) {
                    delay(3000)
                    val splashAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.animation_splash)
                    binding.apply {
                        ivLogo.visibility = View.VISIBLE
                        binding.ivLogo.startAnimation(splashAnim)
                    }
                    delay(1200)
                    isFirstCheck = false
                }

                if (it) {
                    withContext(Dispatchers.Main) {
                        if (isTokenValid) {
                            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
                        } else {
                            findNavController().navigate(R.id.action_splashFragment_to_logInFragment)
                        }
                    }
                } else {
                    showToast(requireContext(), "네트워크 상태를 확인해주세요", Types.ToastType.WARNING)
                }
            }
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