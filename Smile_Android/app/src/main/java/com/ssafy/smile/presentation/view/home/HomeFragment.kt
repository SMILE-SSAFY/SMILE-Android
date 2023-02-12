package com.ssafy.smile.presentation.view.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.ssafy.smile.Application
import com.ssafy.smile.R
import com.ssafy.smile.common.util.*
import com.ssafy.smile.databinding.FragmentHomeBinding
import com.ssafy.smile.domain.model.AddressDomainDto
import com.ssafy.smile.domain.model.CustomPhotographerDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.HomeRecyclerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.view.MainFragmentDirections
import com.ssafy.smile.presentation.viewmodel.home.HomeViewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homeRecyclerAdapter: HomeRecyclerAdapter
    private val recyclerData = mutableListOf<CustomPhotographerDomainDto>()
    private var recyclerViewState: Parcelable? = null
    private var isPhotographer = true
    private var curAddress = ""
    private var userId = -1L
    private var filter = "heart"

    override fun onResume() {
        super.onResume()
        setObserverBeforeSetAddress()
        homeViewModel.getCurrentAddress()

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("Role")?.observe(viewLifecycleOwner){
            homeViewModel.changeRole(requireContext(), Types.Role.getRoleType(it))
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<AddressDomainDto>("curAddress")?.observe(viewLifecycleOwner){
            homeViewModel.getCurrentAddress()
        }

        if (recyclerViewState != null) {
            CommonUtils.setSavedRecyclerViewState(recyclerViewState, binding.rvHome)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun initView() {
        isPhotographer = getRole()
        userId = getUserId()
        initRecycler()
        initToolbar()

        binding.chipPopular.apply {
            isChecked = true
            isEnabled = false
        }
    }

    private fun setObserverBeforeSetAddress() {
        getRoleObserver()
        getAddressObserver()
    }

    private fun setObserverAfterSetAddress() {
        getPhotographerInfoByAddressResponseObserver()
        photographerHeartResponseObserver()
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
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()
                    setProfile(it.data.photoUrl)
                    if (it.data.photographer.size == 0) {
                        recyclerData.clear()
                        homeRecyclerAdapter.notifyDataSetChanged()
                        setIsEmptyView(View.VISIBLE, View.GONE, "해당 주소에 작가님이 존재하지 않습니다")
                    } else {
                        recyclerData.clear()
                        for (i in 0 until it.data.photographer.size) {
                            recyclerData.add(it.data.toCustomPhotographerDomainDto(i))
                        }
                        homeRecyclerAdapter.notifyDataSetChanged()
                        setIsEmptyView(View.GONE, View.VISIBLE, null)
                    }
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                }
            }
        }
    }

    private fun setIsEmptyView(emptyView: Int, recyclerView: Int, emptyViewText: String?) {
        binding.apply {
            layoutEmptyView.layoutEmptyView.visibility = emptyView
            layoutEmptyView.tvEmptyView.text = emptyViewText
            rvHome.visibility = recyclerView
        }
    }

    private fun photographerHeartResponseObserver() {
        homeViewModel.photographerHeartResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Loading -> {
                }
                is NetworkUtils.NetworkResponse.Success -> {
                    homeRecyclerAdapter.notifyDataSetChanged()
                    homeViewModel.getPhotographerInfoByAddressInfo(curAddress, filter)
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    showToast(requireContext(), "작가 좋아요 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.ERROR)
                }
            }
        }
    }

    private fun getAddressObserver() {
        homeViewModel.getCurrentAddressResponse.observe(viewLifecycleOwner) {
            if (it==null) {
                curAddress = getString(R.string.tv_address_unselected)
                binding.tvToolbarAddress.text = curAddress
            } else{
                curAddress = it.address
                binding.tvToolbarAddress.text = AddressUtils.getRepresentAddress(curAddress)
                homeViewModel.getPhotographerInfoByAddressInfo(curAddress, filter)
                setObserverAfterSetAddress()
                if (!(Application.isRecommendRefused)) recommendDialogInit(requireContext())
            }
        }
    }


    override fun setEvent() {
        setRefreshLayoutEvent()
        setChipEvent()
    }

    private fun setRefreshLayoutEvent() {
        binding.apply {
            refreshLayout.setOnRefreshListener {
                homeViewModel.getPhotographerInfoByAddressInfo(curAddress, filter)
                refreshLayout.isRefreshing = false

                when(filter) {
                    "heart" -> {
                        binding.chipPopular.apply {
                            isChecked = true
                            isEnabled = false
                        }
                    }
                    "score" -> {
                        binding.chipReviewAvg.apply {
                            isChecked = true
                            isEnabled = false
                        }
                    }
                    "review" -> {
                        binding.chipReviewCnt.apply {
                            isChecked = true
                            isEnabled = false
                        }
                    }
                }
            }
        }
        setClickListener()
    }

    private fun setChipEvent() {
        binding.apply {
            chipPopular.setOnClickListener {
                setChipsEnabled(popular = false, avg = true, cnt = true)
                filter = if (chipPopular.isChecked) {
                    "heart"
                } else {
                    ""
                }
                homeViewModel.getPhotographerInfoByAddressInfo(curAddress, filter)
            }
            chipReviewAvg.setOnClickListener {
                setChipsEnabled(popular = true, avg = false, cnt = true)
                filter = if (chipReviewAvg.isChecked) {
                    "score"
                } else {
                    ""
                }
                homeViewModel.getPhotographerInfoByAddressInfo(curAddress, filter)
            }
            chipReviewCnt.setOnClickListener {
                setChipsEnabled(popular = true, avg = true, cnt = false)
                filter = if (chipReviewCnt.isChecked) {
                    "review"
                } else {
                    ""
                }
                homeViewModel.getPhotographerInfoByAddressInfo(curAddress, filter)
            }
        }
    }

    private fun setChipsEnabled(popular: Boolean, avg: Boolean, cnt: Boolean) {
        binding.apply {
            chipPopular.isEnabled = popular
            chipReviewAvg.isEnabled = avg
            chipReviewCnt.isEnabled = cnt
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

    private fun setProfile(photoUrl: String) {
        binding.tbHome.apply {
            Glide.with(this)
                .asBitmap()
                .load(Constants.IMAGE_BASE_URL + photoUrl)
                .circleCrop()
                .into(object: CustomTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        menu.findItem(R.id.action_portfolio).icon = BitmapDrawable(resources, resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
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
                    recyclerViewState = CommonUtils.saveRecyclerViewState(binding.rvHome)
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

    private fun recommendDialogInit(context: Context){
        CustomRecommendDialog(context).apply {
            setButtonClickListener(object : CustomRecommendDialog.OnButtonClickListener{
                override fun onOkButtonClick() {
                    val action = MainFragmentDirections.actionMainFragmentToRecommendResultFragment(curAddress)
                    findNavController().navigate(action)
                }

                override fun onCancelButtonClick() { Application.isRecommendRefused = true }
            })
            show()
        }
    }

    private fun moveToAddressGraph() {
        val action = MainFragmentDirections.actionMainFragmentToAddressGraph(true)
        findNavController().navigate(action)
    }

}