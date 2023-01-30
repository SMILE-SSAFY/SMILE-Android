package com.ssafy.smile.presentation.view.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.util.SharedPreferencesUtil
import com.ssafy.smile.databinding.FragmentHomeBinding
import com.ssafy.smile.domain.model.CustomPhotographerDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.HomeRecyclerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.view.MainFragmentDirections
import com.ssafy.smile.presentation.viewmodel.home.HomeViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    private val homeViewModel by activityViewModels<HomeViewModel>()
    private lateinit var homeRecyclerAdapter: HomeRecyclerAdapter
    private val recyclerData = mutableListOf<CustomPhotographerDomainDto>()
    private var isPhotographer = true
    private var curAddress = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun initView() {
        //TODO : 서버 통신 되면 주석 풀기
//        isPhotographer = getRole()
//        curAddress = getAddress()
        initToolbar()
//        homeViewModel.getPhotographerInfoByAddressInfo(curAddress)
//        setObserver()
        initRecycler()
    }

    private fun setObserver() {
        getPhotographerInfoByAddressResponseObserver()
        photographerHeartResponseObserver()
    }

    private fun getPhotographerInfoByAddressResponseObserver() {
        homeViewModel.getPhotographerInfoByAddressResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()
                    recyclerData.clear()
                    it.data.forEach { data ->
                        recyclerData.add(data.toCustomPhotographerDomainDto())
                    }
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    showToast(requireContext(), "주변 작가 목록 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
            }
        }
    }

    private fun photographerHeartResponseObserver() {
        homeViewModel.photographerHeartResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()
                    homeRecyclerAdapter.notifyDataSetChanged()
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    showToast(requireContext(), "작가 좋아요 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
            }
        }
    }

    override fun setEvent() {
        setRefreshLayoutEvent()
    }

    private fun setRefreshLayoutEvent() {
        binding.apply {
            refreshLayout.setOnRefreshListener {
                homeViewModel.getPhotographerInfoByAddressInfo(curAddress)
                refreshLayout.isRefreshing = false
            }
        }
    }

    private fun initToolbar() {
        binding.apply {
            tbHome.apply {
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
            tvToolbarAddress.text = curAddress
        }
    }

    private fun getRole() = SharedPreferencesUtil(requireContext()).getRole() == Types.Role.PHOTOGRAPHER.toString()

    private fun getAddress() {
        //TODO : 주소록 구현 후 curAddress 변수로 주소 가져오기
    }

    private fun initRecycler() {
        homeRecyclerAdapter = HomeRecyclerAdapter(requireContext(), recyclerData).apply {
            setPhotographerHeartItemClickListener(object : HomeRecyclerAdapter.OnPhotographerHeartItemClickListener{
                override fun onClick(view: View, position: Int) {
                    homeViewModel.photographerHeart(recyclerData[position].photographerId)
                }
            })
            setItemClickListener(object: HomeRecyclerAdapter.OnItemClickListener{
                override fun onClick(view: View, position: Int) {
                    val action = MainFragmentDirections.actionMainFragmentToPortfolioFragment(recyclerData[position].photographerId)
                    findNavController().navigate(action)
                }

            })
        }

        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeRecyclerAdapter
        }
    }
}