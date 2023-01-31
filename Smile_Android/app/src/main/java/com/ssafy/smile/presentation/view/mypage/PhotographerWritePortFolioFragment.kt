package com.ssafy.smile.presentation.view.mypage

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.ssafy.smile.R
import com.ssafy.smile.RegisterPortFolioGraphArgs
import com.ssafy.smile.databinding.FragmentWritePhotographerPortfolioBinding
import com.ssafy.smile.domain.model.CategoryDto
import com.ssafy.smile.domain.model.PlaceDomainDto
import com.ssafy.smile.domain.model.Spinners
import com.ssafy.smile.presentation.adapter.CategoryRVAdapter
import com.ssafy.smile.presentation.adapter.PlaceRVAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.mypage.PhotographerWriteGraphViewModel

// TODO : 빈칸 체크 & 동적 리사이클러뷰 View 다듬기
class PhotographerWritePortFolioFragment : BaseFragment<FragmentWritePhotographerPortfolioBinding>
    (FragmentWritePhotographerPortfolioBinding::bind, R.layout.fragment_write_photographer_portfolio) {
    private val navArgs : RegisterPortFolioGraphArgs by navArgs()
    private val viewModel : PhotographerWriteGraphViewModel by navGraphViewModels(R.id.registerPortFolioGraph)
    private lateinit var categoryRVAdapter: CategoryRVAdapter
    private lateinit var placeRVAdapter : PlaceRVAdapter

    //private val photographerResponseDto = PhotographerResponseDto()

    override fun initView() {
        if (navArgs.photographerResponseDto!=null) {
            // TODO : 가져와서 Setting (수정하기 시나리오의 경우)
        }
        initToolbar()
        initAdapter()
        setObserver()
    }
    override fun setEvent() {
        setClickListener()
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("작가 포트폴리오 정보", true) {
            findNavController().navigate(R.id.action_writePortfolioFragment_pop)
        }
    }

    private fun setObserver(){
        viewModel.apply {
            profileImageResponse.observe(viewLifecycleOwner){
                Glide.with(binding.imagePhotographerProfile)
                    .load(it)
                    .into(binding.imagePhotographerProfile)
            }
        }
    }
    private fun setClickListener(){
        binding.apply {
            btnPhotographerProfileChange.setOnClickListener {
                findNavController().navigate(R.id.action_writePortfolioFragment_to_photographerProfileFragment)
            }
            layoutPhotographerCategory.btnAdd.setOnClickListener {
                categoryRVAdapter.addData()
            }
            layoutPhotographerPlace.btnAdd.setOnClickListener {
                placeRVAdapter.addData()
            }
            // TODO : 빈 값 체크!!!
            btnUpload.setOnClickListener {

            }
        }
    }

    private fun initAdapter(){
        categoryRVAdapter = CategoryRVAdapter(binding.layoutPhotographerCategory.btnAdd).apply {
            setItemClickListener(object :CategoryRVAdapter.ItemClickListener{
                override fun onClickBtnDelete(view: View, position: Int, dto: CategoryDto) {
                    categoryRVAdapter.deleteItem(position)
                }
            })
        }.also { binding.layoutPhotographerCategory.rvPhotographerCategory.adapter = it }
        placeRVAdapter = PlaceRVAdapter(binding.layoutPhotographerPlace.btnAdd).apply {
            setItemClickListener(object :PlaceRVAdapter.ItemClickListener{
                override fun onClickBtnDelete(view: View, position: Int, dto: PlaceDomainDto) {
                    placeRVAdapter.deleteItem(position)
                }
            })
        }.also { binding.layoutPhotographerPlace.rvPhotographerPlace.adapter = it }
        placeRVAdapter.setListData(arrayListOf<PlaceDomainDto>(PlaceDomainDto(true,"서울특별시", "강남구", "서울특별시 강남구")))

        binding.layoutPhotographerAccount.apply {
            tvPhotographerAccount.setAdapter(Spinners.getSelectedArrayAdapter(requireContext(), R.array.spinner_account))
            tvPhotographerAccount.setText("카카오뱅크")
            etPhotographerAccount.setText("3333-02-1234567")
            etPhotographerAccount.isEnabled = false
        }
    }

}