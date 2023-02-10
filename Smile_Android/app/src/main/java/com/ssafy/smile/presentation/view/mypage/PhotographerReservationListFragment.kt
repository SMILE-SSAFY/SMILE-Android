package com.ssafy.smile.presentation.view.mypage

import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.ReservationChangeRequestDto
import com.ssafy.smile.databinding.FragmentPhotographerReservationListBinding
import com.ssafy.smile.domain.model.CustomReservationDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.PhotographerReservationListRecyclerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.mypage.PhotographerReservationListViewModel

private const val TAG = "PhotographerReservation_스마일"
class PhotographerReservationListFragment : BaseFragment<FragmentPhotographerReservationListBinding>(FragmentPhotographerReservationListBinding::bind, R.layout.fragment_photographer_reservation_list) {
    private val photographerReservationListViewModel : PhotographerReservationListViewModel by viewModels()

    private lateinit var photographerReservationListRecyclerAdapter: PhotographerReservationListRecyclerAdapter
    private val recyclerData = mutableListOf<CustomReservationDomainDto>()

    override fun initView() {
        initToolbar()
        photographerReservationListViewModel.getPhotographerReservationList()
        setObserver()
        initRecycler()
    }

    private fun setObserver() {
        photographerReservationListObserver()
        changeReservationObserver()
    }

    private fun changeReservationObserver() {
        photographerReservationListViewModel.changeReservationStatusResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    Log.d(TAG, "changeReservationObserver: ${it.errorCode}")
                    showToast(requireContext(), "예약 상태 변경 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.ERROR)
                }
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()
                    photographerReservationListViewModel.getPhotographerReservationList()
                }
            }
        }

        photographerReservationListViewModel.cancelReservationResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    Log.d(TAG, "changeReservationObserver: ${it.errorCode}")
                    showToast(requireContext(), "예약 상태 변경 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.ERROR)
                }
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()
                    photographerReservationListViewModel.getPhotographerReservationList()
                }
            }
        }
    }

    private fun photographerReservationListObserver() {
        photographerReservationListViewModel.getPhotographerReservationListResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()

                    if (it.data.size == 0) {
                        recyclerData.clear()
                        photographerReservationListRecyclerAdapter.notifyDataSetChanged()
                        setIsEmptyView(View.VISIBLE, View.GONE, "예약 내역이 존재하지 않습니다")
                    } else {
                        recyclerData.clear()
                        it.data.forEach { reservation ->
                            recyclerData.add(reservation.toCustomReservationDomainDto())
                        }
                        photographerReservationListRecyclerAdapter.notifyDataSetChanged()
                        setIsEmptyView(View.GONE, View.VISIBLE, null)
                    }
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    Log.d(TAG, "photographerReservationListObserver: ${it.errorCode}")
                    showToast(requireContext(), "작가 예약 조회 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.ERROR)
                }
            }
        }
    }

    private fun setIsEmptyView(emptyView: Int, recyclerView: Int, emptyViewText: String?) {
        binding.apply {
            layoutEmptyView.layoutEmptyView.visibility = emptyView
            layoutEmptyView.tvEmptyView.text = emptyViewText
            recycler.visibility = recyclerView
        }
    }

    private fun initRecycler() {
        photographerReservationListRecyclerAdapter = PhotographerReservationListRecyclerAdapter(requireContext(), recyclerData).apply {
            setCancelClickListener(object : PhotographerReservationListRecyclerAdapter.OnCancelClickListener{
                override fun onClick(view: View, position: Int) {
                    photographerReservationListViewModel.cancelReservation(recyclerData[position].reservationId)
                }
            })
            setResFixClickListener(object : PhotographerReservationListRecyclerAdapter.OnResFixClickListener{
                override fun onClick(view: View, position: Int) {
                    photographerReservationListViewModel.changeReservationStatus(recyclerData[position].reservationId, ReservationChangeRequestDto(1))
                }
            })
        }

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = photographerReservationListRecyclerAdapter
        }
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("예약 일정 현황", true) { moveToPopUpSelf() }
    }

    private fun moveToPopUpSelf() = findNavController().navigate(R.id.action_customerReservationListFragment_pop)

    override fun setEvent() {
        setRefreshLayoutEvent()
    }

    private fun setRefreshLayoutEvent() {
        binding.apply {
            refreshLayout.setOnRefreshListener {
                photographerReservationListViewModel.getPhotographerReservationList()
                refreshLayout.isRefreshing = false
            }
        }
    }
}