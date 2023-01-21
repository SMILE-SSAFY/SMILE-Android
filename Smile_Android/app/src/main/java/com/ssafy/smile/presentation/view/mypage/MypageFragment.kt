package com.ssafy.smile.presentation.view.mypage

import androidx.fragment.app.viewModels
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentMyPageBinding
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.mypage.MyPageViewModel

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page) {
    private val viewModel : MyPageViewModel by viewModels()

    override fun initView() {
        setObserver()
    }

    override fun setEvent() {
        setClickListener()
    }

    private fun setObserver(){
        viewModel.apply {

        }
    }

    private fun setClickListener(){
        binding.apply {
            layoutMyPageProfile.apply {  }
            layoutMyPageUserDetails.apply {
                tvCustomerReservation.setOnClickListener{ }
                tvCustomerInterest.setOnClickListener { }
            }
            layoutMyPagePhotographer.apply {
                clPhotographerRegister.setOnClickListener {  }
                tvPhotographerReservation.setOnClickListener {  }
                tvPhotographerPortfolio.setOnClickListener {  }
                tvPhotographerMonthTax.setOnClickListener {  }
            }
            layoutMyPageUser.apply {
                tvUserChangePassword.setOnClickListener {  }
                tvUserWithDraw.setOnClickListener {  }
            }
        }
    }
}