package com.ssafy.smile.presentation.view.mypage

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentWritePhotographerPortfolioBinding
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.mypage.PhotographerWriteViewModel

class PhotographerWriteFragment : BaseFragment<FragmentWritePhotographerPortfolioBinding>
    (FragmentWritePhotographerPortfolioBinding::bind, R.layout.fragment_write_photographer_portfolio) {
    private val viewModel : PhotographerWriteViewModel by viewModels()

    override fun initView() {
        initToolbar()
        setObserver()
    }
    override fun setEvent() {
        setClickListener()
    }
    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("회원정보수정", true) {
            findNavController().navigate(R.id.action_writePortfolioFragment_pop)
        }
    }
    private fun setObserver(){
        viewModel.apply {  }
    }
    private fun setClickListener(){

    }
}