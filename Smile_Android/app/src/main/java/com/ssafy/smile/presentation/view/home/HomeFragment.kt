package com.ssafy.smile.presentation.view.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.MainActivity
import com.ssafy.smile.R
import com.ssafy.smile.common.util.SharedPreferencesUtil
import com.ssafy.smile.databinding.FragmentHomeBinding
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    private var isPhotographer = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

//        isPhotographer = getRole()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setToolbar()
        }
    }

    override fun initView() {
        (activity as MainActivity).setToolBar(isUsed = false, isBackUsed = false, title = null)
    }

    override fun setEvent() {
    }

    private fun setToolbar() {
        binding.tbHome.apply {
            inflateMenu(R.menu.menu_home)

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_search -> {
                        // TODO : 검색 화면
                        true
                    }
                    R.id.action_portfolio -> {
                        findNavController().navigate(R.id.action_mainFragment_to_portfolioFragment)
                        true
                    }
                    else -> false
                }
            }

            menu.findItem(R.id.action_portfolio).isVisible = isPhotographer
        }
    }

    private fun getRole() = SharedPreferencesUtil(requireContext()).getRole() == Types.Role.PHOTOGRAPHER.toString()
}