package com.ssafy.smile.presentation.view.mypage

import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.ssafy.smile.R
import com.ssafy.smile.RegisterPortFolioGraphArgs
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.util.SharedPreferencesUtil
import com.ssafy.smile.common.util.getString
import com.ssafy.smile.data.remote.model.PhotographerDto
import com.ssafy.smile.data.remote.model.PhotographerRequestDto
import com.ssafy.smile.databinding.FragmentWritePhotographerPortfolioBinding
import com.ssafy.smile.domain.model.CategoryDomainDto
import com.ssafy.smile.domain.model.PlaceDomainDto
import com.ssafy.smile.domain.model.Spinners
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.CategoryRVAdapter
import com.ssafy.smile.presentation.adapter.PlaceRVAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.mypage.PhotographerWriteGraphViewModel

private const val TAG = "PhotographerWritePortFo"
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
            registerPhotographerResponse.observe(viewLifecycleOwner){
                when(it){
                    is NetworkUtils.NetworkResponse.Loading -> {
                        showLoadingDialog(requireContext())
                    }
                    is NetworkUtils.NetworkResponse.Success -> {
                        dismissLoadingDialog()
                        showToast(requireContext(), getString(R.string.msg_photographer_success), Types.ToastType.SUCCESS)
                        findNavController().previousBackStackEntry?.savedStateHandle?.set("Role", Types.Role.PHOTOGRAPHER.getValue())
                        moveToPop()
                    }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        showToast(requireContext(), requireContext().getString(R.string.msg_common_error, "포트폴리오 등록"), Types.ToastType.ERROR)
                        dismissLoadingDialog()
                    }
                }
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
            // TODO : 빈 값 체크!!! - 분기 처리 (값은 다 viewModel에 있어야 함.)
            btnUpload.setOnClickListener {
                val dto = getPhotographerDto()
                viewModel.registerPhotographerInfo(photographerRequestDto = PhotographerRequestDto(viewModel.profileImage!!, dto))
            }
        }
    }

    private fun initAdapter(){
        categoryRVAdapter = CategoryRVAdapter(binding.layoutPhotographerCategory.btnAdd).apply {
            setItemClickListener(object :CategoryRVAdapter.ItemClickListener{
                override fun onClickBtnDelete(view: View, position: Int, dto: CategoryDomainDto) {
                    categoryRVAdapter.deleteData(position)
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
        placeRVAdapter.setListData(arrayListOf(PlaceDomainDto(true,"서울특별시", "강남구")))

        binding.layoutPhotographerAccount.apply {
            tvPhotographerAccount.setAdapter(Spinners.getSelectedArrayAdapter(requireContext(), R.array.spinner_account))
        }
    }
    
    private fun getPhotographerDto(): PhotographerDto {
        val categoryList = categoryRVAdapter.getListData().map {
            it.toCategoryDto() 
        }
        val placeList = placeRVAdapter.getListData().map { it.toPlaceDto() }
        return PhotographerDto(
            account = "${binding.layoutPhotographerAccount.tvPhotographerAccount.text} ${binding.layoutPhotographerAccount.etPhotographerAccount.text}",
            categories = categoryList,
            places = placeList,
            introduction = binding.etPhotographerInfo.getString()
        )
    }

    private fun moveToPop() = findNavController().navigate(R.id.action_writePortfolioFragment_pop)

}