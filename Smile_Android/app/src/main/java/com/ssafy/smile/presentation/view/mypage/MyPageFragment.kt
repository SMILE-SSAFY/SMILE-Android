package com.ssafy.smile.presentation.view.mypage

import android.app.ProgressDialog.show
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.R
import com.ssafy.smile.common.view.CommonDialog
import com.ssafy.smile.databinding.FragmentMyPageBinding
import com.ssafy.smile.domain.model.DialogBody
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
        viewModel.apply {  }
    }

    private fun setClickListener(){
        binding.apply {
            layoutMyPageProfile.apply {  }
            layoutMyPageUserDetails.apply {
                tvCustomerReservation.setOnClickListener{ }
                tvCustomerInterest.setOnClickListener { }
            }
            layoutMyPagePhotographer.apply {
                clPhotographerWritePortfolio.setOnClickListener {
                    val action = {findNavController().navigate(R.id.action_mainFragment_to_writePortfolioFragment)}
                    val dialog = CommonDialog(requireContext(), DialogBody("작가 등록을 하시겠습니까?", "작가 등록"), action)
                    showDialog(dialog, viewLifecycleOwner)
                }
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