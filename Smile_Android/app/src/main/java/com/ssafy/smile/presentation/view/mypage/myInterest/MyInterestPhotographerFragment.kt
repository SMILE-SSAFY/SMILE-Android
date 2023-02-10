package com.ssafy.smile.presentation.view.mypage.myInterest

import android.util.Log
import android.view.View
import android.widget.CheckedTextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.databinding.FragmentMyInterestPhotographerBinding
import com.ssafy.smile.domain.model.CustomPhotographerDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.MyInterestPhotographerRVAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.mypage.MyInterestViewModel

private const val TAG = "MyInterestPhotographerF_스마일"
class MyInterestPhotographerFragment : BaseFragment<FragmentMyInterestPhotographerBinding>(FragmentMyInterestPhotographerBinding::bind, R.layout.fragment_my_interest_photographer) {
    private val viewModel : MyInterestViewModel by viewModels()
    private lateinit var rvMyInterestPhotographerAdapter : MyInterestPhotographerRVAdapter


    override fun initView() {
        initRVAdapter()
    }

    override fun setEvent() {
        setObserver()
        getInterestInfo()
    }

    private fun setObserver(){
        viewModel.apply {
            getPhotographerHeartListResponse.observe(viewLifecycleOwner){
                when(it) {
                    is NetworkUtils.NetworkResponse.Loading -> {
                        showLoadingDialog(requireContext())
                    }
                    is NetworkUtils.NetworkResponse.Success -> {
                        dismissLoadingDialog()
                        if (it.data.isEmpty()){
                            setEmptyView(true)
                            setRVView(false)
                        }else{
                            val list = it.data.map { dto -> dto.toCustomPhotographerDomainDto() }
                            setEmptyView(false)
                            setRVView(true, list as ArrayList<CustomPhotographerDomainDto>)
                        }
                    }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        dismissLoadingDialog()
                        showToast(requireContext(), requireContext().getString(R.string.msg_common_error, "관심 작가를 불러오는"), Types.ToastType.ERROR)
                    }
                }
            }
            postPhotographerHeartResponse.observe(viewLifecycleOwner){
                when(it) {
                    is NetworkUtils.NetworkResponse.Loading -> { }
                    is NetworkUtils.NetworkResponse.Success -> { getInterestInfo() }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        showToast(requireContext(), requireContext().getString(R.string.msg_common_error, "찜 설정"), Types.ToastType.ERROR)
                    }
                }
            }
        }
    }

    private fun getInterestInfo(){
        viewModel.getPhotographerHeartList()
    }

    private fun initRVAdapter(){
        rvMyInterestPhotographerAdapter = MyInterestPhotographerRVAdapter().apply {
            setItemClickListener(object : MyInterestPhotographerRVAdapter.ItemClickListener{
                override fun onClickItem(view: View, position: Int, photographerDto: CustomPhotographerDomainDto) {
                    moveToPortfolioGraph(photographerDto)
                }
                override fun onClickHeart(view: CheckedTextView, position: Int, photographerDto: CustomPhotographerDomainDto) {
                    view.isChecked = !(view.isChecked)
                    viewModel.postPhotographerHeart(photographerDto.photographerId)
                }
            })
        }
        binding.recycleView.adapter = rvMyInterestPhotographerAdapter
    }

    private fun setEmptyView(isSet : Boolean){
        if (isSet){
            binding.layoutEmptyView.layoutEmptyView.visibility = View.VISIBLE
            binding.layoutEmptyView.tvEmptyView.text = getString(R.string.tv_interest_photographer_empty)
        }else binding.layoutEmptyView.layoutEmptyView.visibility = View.GONE
    }

    private fun setRVView(isSet : Boolean, itemList : ArrayList<CustomPhotographerDomainDto> = arrayListOf()){
        if (isSet) {
            binding.recycleView.visibility = View.VISIBLE
            rvMyInterestPhotographerAdapter.setListData(itemList)
        } else binding.recycleView.visibility = View.GONE
    }

    private fun moveToPortfolioGraph(photographerDto : CustomPhotographerDomainDto){
        val action = MyInterestFragmentDirections.actionMyInterestFragmentToPortfolioGraph(photographerDto.photographerId, -1L)
        findNavController().navigate(action)
    }

}