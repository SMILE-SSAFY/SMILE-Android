package com.ssafy.smile.presentation.view.mypage


import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.CommonDialog
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import com.ssafy.smile.databinding.FragmentMyPageBinding
import com.ssafy.smile.domain.model.DialogBody
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.view.MainFragmentDirections
import com.ssafy.smile.presentation.viewmodel.mypage.MyPageViewModel


class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page) {
    private val viewModel : MyPageViewModel by viewModels()
    private var isPhotographer : Boolean = false   // TODO : 임시 처리 -> LiveData 처리 필요해보인다.. (setUserView, showPhotographerWriteDialog 동시 조절)

    override fun initView() {
        initToolbar()
        setObserver()
        setPhotographerLayoutView(isPhotographer)
    }
    override fun setEvent() {
        setClickListener()
    }
    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("마이페이지", false)
    }

    private fun setObserver(){
        viewModel.apply {
            getPhotographerResponse.observe(viewLifecycleOwner){
                when(it){
                    is NetworkUtils.NetworkResponse.Loading -> { showLoadingDialog(requireContext()) }
                    is NetworkUtils.NetworkResponse.Success -> { moveToRegisterPortFolioGraph(it.data) }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        showToast(requireContext(), "정보를 가져오는 중에 오류가 발생했습니다.\n잠시 후, 다시 시도해주세요.", Types.ToastType.ERROR)
                    }
                }
            }
            deletePhotographerResponse.observe(viewLifecycleOwner){
                when(it){
                    is NetworkUtils.NetworkResponse.Loading -> { showLoadingDialog(requireContext()) }
                    is NetworkUtils.NetworkResponse.Success -> {
                        // TODO : sharedPreference - Role 값 변경 -> 이를 위에서 정의한 LiveData로 알려줘야 할 듯
                    }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        showToast(requireContext(), "정보 삭제 중에 오류가 발생했습니다.\n잠시 후, 다시 시도해주세요.", Types.ToastType.ERROR)
                    }
                }
            }
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
                clPhotographerWritePortfolio.setOnClickListener {
                    showPhotographerWriteDialog(isPhotographer)
                }
                tvPhotographerReservation.setOnClickListener {  }
                tvPhotographerPortfolio.setOnClickListener { viewModel.getPhotographerInfo() }
            }
            layoutMyPageUser.apply {
                tvUserChangePassword.setOnClickListener {  }
                tvUserWithDraw.setOnClickListener {  }
            }
        }
    }
    private fun setPhotographerLayoutView(isPhotographer : Boolean){
        binding.layoutMyPagePhotographer.apply {
            if (isPhotographer){
                smPhotographerWritePortfolio.isChecked = true
                llAuthorDetail.visibility = View.VISIBLE
            }else {
                smPhotographerWritePortfolio.isChecked = false
                llAuthorDetail.visibility = View.GONE
            }
        }
    }
    private fun showPhotographerWriteDialog(isPhotographer : Boolean){
        if (isPhotographer){
            val dialog = CommonDialog(requireContext(), DialogBody(resources.getString(R.string.delete_photographer_portfolio), "등록 삭제"), { viewModel.deletePhotographerInfo() })
            showDialog(dialog, viewLifecycleOwner)
        }else {
            MainFragmentDirections.actionMainFragmentToRegisterPortFolioGraph()
            val dialog = CommonDialog(requireContext(), DialogBody(resources.getString(R.string.add_photographer_portfolio), "작가 등록"), { moveToRegisterPortFolioGraph(null) })
            showDialog(dialog, viewLifecycleOwner)
        }
    }

    private fun moveToRegisterPortFolioGraph(photographerResponseDto: PhotographerResponseDto?) {
       val action = MainFragmentDirections.actionMainFragmentToRegisterPortFolioGraph(photographerResponseDto)
        findNavController().navigate(action)
    }
}