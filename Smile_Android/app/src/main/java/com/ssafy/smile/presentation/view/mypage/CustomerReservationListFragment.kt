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
import com.ssafy.smile.databinding.FragmentCustomerReservationListBinding
import com.ssafy.smile.domain.model.CustomReservationDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.CustomerReservationListRecyclerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.mypage.CustomerReservationListViewModel

private const val TAG = "CustomerReservationList_싸피"
class CustomerReservationListFragment() : BaseFragment<FragmentCustomerReservationListBinding>(FragmentCustomerReservationListBinding::bind, R.layout.fragment_customer_reservation_list) {
    private val customerReservationListViewModel: CustomerReservationListViewModel by viewModels()

    private lateinit var customerReservationListRecyclerAdapter: CustomerReservationListRecyclerAdapter
    private val recyclerData = mutableListOf<CustomReservationDomainDto>()

    override fun onResume() {
        super.onResume()
        customerReservationListViewModel.getCustomerReservationList()
    }

    override fun initView() {
        initToolbar()
        customerReservationListViewModel.getCustomerReservationList()
        setObserver()
        initRecycler()
    }

    private fun setObserver() {
        customerReservationListObserver()
        changeReservationObserver()
    }

    private fun customerReservationListObserver() {
        customerReservationListViewModel.getCustomerReservationListResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()

                    if (it.data.size == 0) {
                        recyclerData.clear()
                        customerReservationListRecyclerAdapter.notifyDataSetChanged()
                        setIsEmptyView(View.VISIBLE, View.GONE, "예약 내역이 존재하지 않습니다")
                    } else {
                        recyclerData.clear()
                        it.data.forEach { reservation ->
                            recyclerData.add(reservation.toCustomReservationDomainDto())
                        }
                        customerReservationListRecyclerAdapter.notifyDataSetChanged()
                        setIsEmptyView(View.GONE, View.VISIBLE, null)
                    }
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    Log.d(TAG, "customerReservationListObserver: ${it.errorCode}")
                    showToast(requireContext(), "고객 예약 조회 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.ERROR)
                }
            }
        }
    }

    private fun changeReservationObserver() {
        customerReservationListViewModel.changeReservationStatusResponse.observe(viewLifecycleOwner) {
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
                    customerReservationListViewModel.getCustomerReservationList()
                }
            }
        }

        customerReservationListViewModel.cancelReservationResponse.observe(viewLifecycleOwner) {
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
                    customerReservationListViewModel.getCustomerReservationList()
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
        customerReservationListRecyclerAdapter = CustomerReservationListRecyclerAdapter(requireContext(), recyclerData).apply {
            setCancelClickListener(object : CustomerReservationListRecyclerAdapter.OnCancelClickListener{
                override fun onClick(view: View, position: Int) {
                    customerReservationListViewModel.cancelReservation(recyclerData[position].reservationId)
                }
            })
            setPayFixClickListener(object : CustomerReservationListRecyclerAdapter.OnPayFixClickListener{
                override fun onClick(view: View, position: Int) {
                    customerReservationListViewModel.changeReservationStatus(recyclerData[position].reservationId, ReservationChangeRequestDto(3))
                }
            })
            setReviewClickListener(object : CustomerReservationListRecyclerAdapter.OnReviewClickListener{
                override fun onClick(view: View, position: Int) {
                    val reservationId = recyclerData[position].reservationId
                    val photographerName = recyclerData[position].name
                    val action = CustomerReservationListFragmentDirections.actionCustomerReservationListFragmentToReviewDetailFragment(photographerName, reservationId)
                    findNavController().navigate(action)
                }
            })
            setReviewCheckClickListener(object : CustomerReservationListRecyclerAdapter.OnReviewCheckClickListener{
                override fun onClick(view: View, position: Int) {
                    val photographerName = recyclerData[position].name
                    val reviewId = recyclerData[position].reviewId
                    val action = CustomerReservationListFragmentDirections.actionCustomerReservationListFragmentToReviewDetailFragment(photographerName, reviewId = reviewId)
                    findNavController().navigate(action)
                }
            })
        }

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = customerReservationListRecyclerAdapter
        }
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("예약/결제 내역", true) { moveToPopUpSelf() }
    }

    private fun moveToPopUpSelf() = findNavController().navigate(R.id.action_customerReservationListFragment_pop)

    override fun setEvent() {
        setRefreshLayoutEvent()
    }

    private fun setRefreshLayoutEvent() {
        binding.apply {
            refreshLayout.setOnRefreshListener {
                customerReservationListViewModel.getCustomerReservationList()
                refreshLayout.isRefreshing = false
            }
        }
    }
}