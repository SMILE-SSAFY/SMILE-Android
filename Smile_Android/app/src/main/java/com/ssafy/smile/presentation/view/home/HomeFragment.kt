package com.ssafy.smile.presentation.view.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smile.R
import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.util.SharedPreferencesUtil
import com.ssafy.smile.databinding.FragmentHomeBinding
import com.ssafy.smile.domain.model.CustomPhotographerDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.HomeRecyclerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.view.MainFragmentDirections
import com.ssafy.smile.presentation.viewmodel.MainViewModel
import com.ssafy.smile.presentation.viewmodel.home.HomeViewModel

private const val TAG = "HomeFragment_스마일"
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homeRecyclerAdapter: HomeRecyclerAdapter
    private val recyclerData = mutableListOf<CustomPhotographerDomainDto>()
    private var isPhotographer = true
    private var curAddress = ""
    private var userId = -1L

    override fun onResume() {
        super.onResume()
        homeViewModel.getCurrentAddressInfo()
        homeViewModel.getPhotographerInfoByAddressInfo(curAddress)
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("Role")?.observe(viewLifecycleOwner){
            homeViewModel.changeRole(requireContext(), Types.Role.getRoleType(it))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun initView() {
        isPhotographer = getRole()
        userId = getUserId()
        initToolbar()
        homeViewModel.getCurrentAddressInfo()
        homeViewModel.getPhotographerInfoByAddressInfo(curAddress)
        setObserver()
        initRecycler()
    }

    private fun setObserver() {
        getPhotographerInfoByAddressResponseObserver()
        photographerHeartResponseObserver()
        getRoleObserver()
        getAddressObserver()
    }

    private fun getRoleObserver() {
        homeViewModel.getRoleLiveData.observe(viewLifecycleOwner){
            isPhotographer = it==Types.Role.PHOTOGRAPHER
            binding.tbHome.menu.findItem(R.id.action_portfolio).isVisible = isPhotographer
        }
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
                    homeRecyclerAdapter.notifyDataSetChanged()
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
                }
                is NetworkUtils.NetworkResponse.Success -> {
                    homeRecyclerAdapter.notifyDataSetChanged()
                    homeViewModel.getPhotographerInfoByAddressInfo(curAddress)
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    showToast(requireContext(), "작가 좋아요 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
            }
        }
    }

    private fun getAddressObserver() {
        homeViewModel.getCurrentAddressResponse.observe(viewLifecycleOwner) {
            curAddress = it.address
            curAddress = CommonUtils.getAddress(requireContext(), it.latitude.toFloat(), it.longitude.toFloat())

            Log.d(TAG, "address : ${it.address}")
            Log.d(TAG, "longitude : ${it.longitude}")
            Log.d(TAG, "latitude : ${it.latitude}")
            Log.d(TAG, "getaddress() : ${CommonUtils.getAddress(requireContext(), it.latitude.toFloat(), it.longitude.toFloat())}")
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
        setClickListener()
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
                            val action = MainFragmentDirections.actionMainFragmentToPortfolioGraph(userId, -1L)
                            findNavController().navigate(action)
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


    private fun setClickListener(){
        binding.apply {
            layoutSearchAddress.setOnClickListener { moveToAddressGraph() }
        }
    }

    private fun getRole() = SharedPreferencesUtil(requireContext()).getRole() == Types.Role.PHOTOGRAPHER.getValue()

    private fun getUserId(): Long = SharedPreferencesUtil(requireContext()).getUserId()

    private fun initRecycler() {
        homeRecyclerAdapter = HomeRecyclerAdapter(requireContext(), recyclerData).apply {
            setPhotographerHeartItemClickListener(object : HomeRecyclerAdapter.OnPhotographerHeartItemClickListener{
                override fun onClick(view: View, position: Int) {
                    homeViewModel.photographerHeart(recyclerData[position].photographerId)
                }
            })
            setItemClickListener(object: HomeRecyclerAdapter.OnItemClickListener{
                override fun onClick(view: View, position: Int) {
                    val action = MainFragmentDirections.actionMainFragmentToPortfolioGraph(recyclerData[position].photographerId, -1L)
                    findNavController().navigate(action)
                }

            })
        }

        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeRecyclerAdapter
        }
    }

    private fun moveToAddressGraph() {
        val action = MainFragmentDirections.actionMainFragmentToAddressGraph(true)
        findNavController().navigate(action)
    }

}