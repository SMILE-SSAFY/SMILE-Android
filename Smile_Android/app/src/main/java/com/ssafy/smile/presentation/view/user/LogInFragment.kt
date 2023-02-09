package com.ssafy.smile.presentation.view.user

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssafy.smile.Application
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.util.SharedPreferencesUtil
import com.ssafy.smile.data.remote.model.KakaoLoginRequestDto
import com.ssafy.smile.databinding.FragmentLogInBinding
import com.ssafy.smile.domain.model.LoginDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.user.LoginViewModel
import com.ssafy.smile.presentation.viewmodel.user.SignUpGraphViewModel

private const val TAG = "LogInFragment_스마일"
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::bind, R.layout.fragment_log_in) {

    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var fcmToken: String

    private val callback : (OAuthToken?, Throwable?) -> Unit = { token, error ->
        // 에러가 난 경우
        if (error != null) {
            Log.d(TAG,"카카오 로그인 실패 - $error")
        }
        // 토근이 발급된 경우
        else if (token != null) {
            UserApiClient.instance.me { user, error ->
                if (user != null) {
                    loginViewModel.kakaoLogin(KakaoLoginRequestDto(token.accessToken))
                } else {
                    Log.d(TAG,"카카오 로그인 실패 - $error")
                }
            }
        }
    }

    override fun initView() {
        getFirebaseToken()
        setObserver()
    }

    private fun setObserver() {
        loginResponseObserver()
        kakaoLoginResponseObserver()
    }

    override fun setEvent() {
        binding.apply {
            btnLogin.setOnClickListener {
                if (checkValid()) {
                    val loginInfo = getLoginInfo()
                    loginViewModel.login(loginInfo)
                }
            }

            btnSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_logInFragment_to_signUpGraph)
            }

            btnSnsLogin.setOnClickListener {
                kakaoLogin()
            }
        }
    }

    private fun loginResponseObserver() {
        loginViewModel.loginResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()
                    SharedPreferencesUtil(requireContext()).putAuthToken("Bearer ${it.data.token}")
                    SharedPreferencesUtil(requireContext()).putAuthTime(System.currentTimeMillis())
                    SharedPreferencesUtil(requireContext()).putRole(it.data.role)
                    SharedPreferencesUtil(requireContext()).putUserId(it.data.userId)
                    findNavController().navigate(R.id.action_logInFragment_to_mainFragment)
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    when (it.errorCode) {
                        404 -> {
                            showToast(requireContext(), "존재하지 않는 회원입니다. 다시 로그인해주세요.", Types.ToastType.INFO)
                        }
                        400 -> {
                            showToast(requireContext(), "비밀번호가 일치하지 않습니다. 다시 로그인해주세요.", Types.ToastType.INFO)
                        }
                        else -> {
                            showToast(requireContext(), "로그인 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.ERROR)
                        }
                    }
                }
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
            }
        }
    }

    private fun kakaoLoginResponseObserver() {
        loginViewModel.kakaoLoginResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()
                    SharedPreferencesUtil(requireContext()).putAuthToken("Bearer ${it.data.token}")
                    SharedPreferencesUtil(requireContext()).putAuthTime(System.currentTimeMillis())
                    SharedPreferencesUtil(requireContext()).putRole(it.data.role)
                    SharedPreferencesUtil(requireContext()).putUserId(it.data.userId)
                    findNavController().navigate(R.id.action_logInFragment_to_mainFragment)
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    showToast(requireContext(), "로그인 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.ERROR)
                }
            }
        }
    }

    private fun checkValid(): Boolean {
        binding.apply {
            return if (etId.text.toString().isEmpty()) {
                showToast(requireContext(), "아이디를 입력해주세요", Types.ToastType.INFO)
                false
            } else if (etPassword.text.toString().isEmpty()) {
                showToast(requireContext(), "비밀번호를 입력해주세요", Types.ToastType.INFO)
                false
            } else {
                true
            }
        }
    }

    private fun getLoginInfo(): LoginDomainDto {
        binding.apply {
            return LoginDomainDto(etId.text.toString(), etPassword.text.toString(), fcmToken)
        }
    }

    private fun kakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                if (error != null) {
                    Log.d(TAG,"카카오 로그인 실패 - $error")

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
                } else if (token != null) {
                    loginViewModel.kakaoLogin(KakaoLoginRequestDto(token.accessToken, fcmToken))
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
        }
    }

    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result?.let {
                    fcmToken = it
                    Application.sharedPreferences.putFCMToken(it)
                }
            } else error("FCM 토큰 얻기에 실패하였습니다. 잠시 후 다시 시도해주세요.")
        }
    }
}