package com.ssafy.smile.presentation.view.home

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smile.R
import com.ssafy.smile.common.util.SharedPreferencesUtil
import com.ssafy.smile.databinding.FragmentHomeBinding
import com.ssafy.smile.domain.model.CustomPhotographerDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.HomeRecyclerAdapter
import com.ssafy.smile.presentation.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    private lateinit var homeRecyclerAdapter: HomeRecyclerAdapter
    private val recyclerData = mutableListOf<CustomPhotographerDomainDto>()
    private var isPhotographer = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        for (i in 1..10) {
            recyclerData.add(
                CustomPhotographerDomainDto(
                    "https://lab.ssafy.com/uploads/-/system/appearance/header_logo/1/ssafy_logo.png",
                    "cccc",
                    "ccccc",
                    "cccc",
                    "cccc",
                    false,
                    100
                )
            )
            recyclerData.add(
                CustomPhotographerDomainDto(
                    "https://lab.ssafy.com/uploads/-/system/appearance/header_logo/1/ssafy_logo.png",
                    "cccc",
                    "ccccc",
                    "cccc",
                    "cccc",
                    false,
                    100
                )
            )
        }

//        isPhotographer = getRole()
    }

    override fun initView() {
        initToolbar()
        initRecycler()
    }

    override fun setEvent() {
    }

    private fun initToolbar() {
        binding.tbHome.apply {
            inflateMenu(R.menu.menu_home)

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_search -> {
                        findNavController().navigate(R.id.action_mainFragment_to_searchResultFragment)
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

    private fun initRecycler() {
        homeRecyclerAdapter = HomeRecyclerAdapter(requireContext(), recyclerData)

        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeRecyclerAdapter
        }
    }
}