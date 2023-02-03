package com.ssafy.smile.presentation.view.mypage


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.MainActivity
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.util.SharedPreferencesUtil
import com.ssafy.smile.common.view.CommonDialog
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import com.ssafy.smile.databinding.FragmentMyPageBinding
import com.ssafy.smile.domain.model.DialogBody
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.view.MainFragmentDirections
import com.ssafy.smile.presentation.viewmodel.mypage.MyPageViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page) {
    private val viewModel : MyPageViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("Role")?.observe(viewLifecycleOwner){
            viewModel.changeRole(requireContext(), Types.Role.getRoleType(it))
        }
    }

    override fun initView() {
        initToolbar()
        setObserver()
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
            viewModel.getRoleLiveData.observe(viewLifecycleOwner){
                val isPhotographer = it==Types.Role.PHOTOGRAPHER
                setPhotographerLayoutView(isPhotographer)
            }
            getPhotographerResponse.observe(viewLifecycleOwner){
                when(it){
                    is NetworkUtils.NetworkResponse.Loading -> { showLoadingDialog(requireContext()) }
                    is NetworkUtils.NetworkResponse.Success -> { moveToRegisterPortFolioGraph(it.data) }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        showToast(requireContext(), requireContext().getString(R.string.msg_common_error, "정보를 가져오는"), Types.ToastType.ERROR)
                    }
                }
            }
            deletePhotographerResponse.observe(viewLifecycleOwner){
                when(it){
                    is NetworkUtils.NetworkResponse.Loading -> { showLoadingDialog(requireContext()) }
                    is NetworkUtils.NetworkResponse.Success -> {
                        dismissLoadingDialog()
                        changeRole(requireContext(), Types.Role.USER)
                    }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        dismissLoadingDialog()
                        showToast(requireContext(), requireContext().getString(R.string.msg_common_error, "정보 삭제"), Types.ToastType.ERROR)
                    }
                }
            }
            withDrawUserResponse.observe(viewLifecycleOwner){
                when(it){
                    is NetworkUtils.NetworkResponse.Loading -> { showLoadingDialog(requireContext()) }
                    is NetworkUtils.NetworkResponse.Success -> {
                        dismissLoadingDialog()
                        logout()
                    }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        dismissLoadingDialog()
                        showToast(requireContext(), requireContext().getString(R.string.msg_common_error, "회원탈퇴"), Types.ToastType.ERROR)
                    }
                }
            }
            getRole(requireContext())
        }
    }
    private fun setClickListener(){
        binding.apply {
            layoutMyPageProfile.apply {
                btnLogout.setOnClickListener {  showLogoutDialog() }
            }
            layoutMyPageUserDetails.apply {
                tvCustomerReservation.setOnClickListener{
                    findNavController().navigate(R.id.action_mainFragment_to_customerReservationListFragment)
                }
                tvCustomerInterest.setOnClickListener {
                    moveToMyInterestFragment()
                }
            }
            layoutMyPagePhotographer.apply {
                clPhotographerWritePortfolio.setOnClickListener {
                    if (smPhotographerWritePortfolio.isChecked) showPhotographerDeleteDialog()
                    else showPhotographerWriteDialog()
                }
                tvPhotographerReservation.setOnClickListener {
                    findNavController().navigate(R.id.action_mainFragment_to_photographerReservationList)
                }
                tvPhotographerPortfolio.setOnClickListener {
                    viewModel.getPhotographerInfo()
                }
            }
            layoutMyPageUser.apply {
                tvUserChangePassword.setOnClickListener {  }
                tvUserWithDraw.setOnClickListener {
                    showPhotographerWithDrawDialog()
                }
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
    private fun logout() {
        try {
            removeUserInfo()

            Intent(context, MainActivity::class.java).apply {
                requireActivity().finish()
                startActivity(this)
            }
            requireActivity().finish()
        } catch (e: IOException) { findNavController().navigate(R.id.action_global_loginFragment) }
    }
    private fun removeUserInfo(){
        SharedPreferencesUtil(requireContext()).removeAllInfo()
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.deleteAllAddress()
        }
    }
    private fun showLogoutDialog(){
        val dialog = CommonDialog(requireContext(), DialogBody(resources.getString(R.string.logout), "로그아웃"), { logout() })
        showDialog(dialog, viewLifecycleOwner)
    }
    private fun showPhotographerWriteDialog(){
        val dialog = CommonDialog(requireContext(), DialogBody(resources.getString(R.string.add_photographer_portfolio), "작가 등록"), { moveToRegisterPortFolioGraph(null) })
        showDialog(dialog, viewLifecycleOwner)
    }
    private fun showPhotographerDeleteDialog(){
        val dialog = CommonDialog(requireContext(), DialogBody(resources.getString(R.string.delete_photographer_portfolio), "등록 삭제"), { viewModel.deletePhotographerInfo() })
        showDialog(dialog, viewLifecycleOwner)
    }
    private fun showPhotographerWithDrawDialog(){
        val dialog = CommonDialog(requireContext(), DialogBody(resources.getString(R.string.withDraw_user), "회원 탈퇴"), { viewModel.withDrawUser() })
        showDialog(dialog, viewLifecycleOwner)
    }
    private fun moveToMyInterestFragment() {
        findNavController().navigate(R.id.action_mainFragment_to_myInterestFragment)
    }
    private fun moveToRegisterPortFolioGraph(photographerResponseDto: PhotographerResponseDto?) {
       val action = MainFragmentDirections.actionMainFragmentToRegisterPortFolioGraph(photographerResponseDto)
        findNavController().navigate(action)
    }

}