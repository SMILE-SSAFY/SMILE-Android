package com.ssafy.smile.presentation.view.mypage

import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.ssafy.smile.R
import com.ssafy.smile.RegisterPortFolioGraphArgs
import com.ssafy.smile.common.util.*
import com.ssafy.smile.common.util.ImageUtils.convertBitmapToFile
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import com.ssafy.smile.databinding.FragmentWritePhotographerPortfolioBinding
import com.ssafy.smile.domain.model.*
import com.ssafy.smile.presentation.adapter.CategoryRVAdapter
import com.ssafy.smile.presentation.adapter.PlaceRVAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.mypage.PhotographerWriteGraphViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.net.URL


// TODO : API 수정 후, 연결 + 처리
class PhotographerWritePortFolioFragment : BaseFragment<FragmentWritePhotographerPortfolioBinding>
    (FragmentWritePhotographerPortfolioBinding::bind, R.layout.fragment_write_photographer_portfolio) {
    private val navArgs : RegisterPortFolioGraphArgs by navArgs()
    private val viewModel : PhotographerWriteGraphViewModel by navGraphViewModels(R.id.registerPortFolioGraph)
    private lateinit var categoryRVAdapter: CategoryRVAdapter
    private lateinit var placeRVAdapter : PlaceRVAdapter
    private val accountDto = AccountDomainDto()


    override fun initView() {
        initToolbar()
        initAdapter()
        setObserver()
        if (navArgs.photographerResponseDto!=null) setModifyPortFolioView(navArgs.photographerResponseDto!!)
        else setWritePortFolioView()
    }
    override fun setEvent() {
        setClickListener()
    }


    private fun setObserver(){
        viewModel.apply {
            profileImageResponse.observe(viewLifecycleOwner){
                Glide.with(binding.imagePhotographerProfile)
                    .load(it)
                    .into(binding.imagePhotographerProfile)
                //viewModel.reloadStoredData()
            }
            photographerDataResponse.observe(viewLifecycleOwner){
                setData(it)
            }
            checkDataResponse.observe(viewLifecycleOwner){
                if (it) setButtonEnable()
                else setButtonDisable()
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
            modifyPhotographerResponse.observe(viewLifecycleOwner){
                when(it){
                    is NetworkUtils.NetworkResponse.Loading -> {
                        showLoadingDialog(requireContext())
                    }
                    is NetworkUtils.NetworkResponse.Success -> {
                        dismissLoadingDialog()
                        showToast(requireContext(), getString(R.string.msg_photographer_modify_success), Types.ToastType.SUCCESS)
                        moveToPop()
                    }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        showToast(requireContext(), requireContext().getString(R.string.msg_common_error, "포트폴리오 수정"), Types.ToastType.ERROR)
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
            btnUpload.setOnClickListener {
                if (navArgs.photographerResponseDto!=null) viewModel.modifyPhotographerInfo()
                else viewModel.registerPhotographerInfo()
            }
        }
    }

    private fun initAdapter(){
        categoryRVAdapter = CategoryRVAdapter(viewModel, binding.layoutPhotographerCategory.btnAdd).apply {
            setItemClickListener(object :CategoryRVAdapter.ItemClickListener{
                override fun onClickBtnDelete(view: View, position: Int, dto: CategoryDomainDto) {
                    categoryRVAdapter.deleteData(position)
                }
            })
        }.also { binding.layoutPhotographerCategory.rvPhotographerCategory.adapter = it }
        placeRVAdapter = PlaceRVAdapter(viewModel, binding.layoutPhotographerPlace.btnAdd).apply {
            setItemClickListener(object :PlaceRVAdapter.ItemClickListener{
                override fun onClickBtnDelete(view: View, position: Int, dto: PlaceDomainDto) {
                    placeRVAdapter.deleteItem(position)
                }
            })
        }.also { binding.layoutPhotographerPlace.rvPhotographerPlace.adapter = it }

        binding.etPhotographerInfo.doOnTextChanged { text, _, _, _ ->
            val introduce = if (text.isNullOrBlank()) null else binding.etPhotographerInfo.getString()
            viewModel.uploadIntroductionData(introduce)
        }
        setAccountAdapter()
    }

    private fun setWritePortFolioView(){
        initToolbar()
        setButtonDisable()
    }

    private fun setModifyPortFolioView(photographerResponseDto: PhotographerResponseDto){
        initToolbar()
        binding.apply {
            btnUpload.text = "수정하기"
            val account = photographerResponseDto.account.split(" ")
            viewModel.uploadData(photographerResponseDto.introduction, photographerResponseDto.categories.map { it.toCategoryDto() }, photographerResponseDto.places.map { it.toPlaceDto() }, AccountDomainDto(false, account[0], account[1]))
            lifecycleScope.launch(Dispatchers.IO) {
                val profileImg = BitmapFactory.decodeStream(URL(Constants.IMAGE_BASE_URL +photographerResponseDto.profileImg).openConnection().getInputStream()).convertBitmapToFile(context = requireContext())!!
                viewModel.uploadProfileImage(profileImg)
            }
        }
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("작가 포트폴리오 정보", true) {
            findNavController().navigate(R.id.action_writePortfolioFragment_pop)
        }
    }

    private fun setAccountAdapter(accountBank:String?=null, accountNum:String?=null) {
        binding.layoutPhotographerAccount.apply {
            tvPhotographerAccount.setOnItemClickListener { _, _, _, _ ->
                accountDto.accountBank = tvPhotographerAccount.getString()
                accountDto.isEmpty = accountDto.accountNum == null
                viewModel.uploadAccountData(accountDto)
            }

            etPhotographerAccount.doOnTextChanged { text, _, _, _ ->
                accountDto.accountNum = if (text.isNullOrBlank()) null else etPhotographerAccount.getString()
                accountDto.isEmpty = accountDto.accountBank == null
                viewModel.uploadAccountData(accountDto)
            }
            tvPhotographerAccount.setText(accountBank)
            tvPhotographerAccount.setAdapter(Spinners.getSelectedArrayAdapter(requireContext(), R.array.spinner_account))
            etPhotographerAccount.setText(accountNum)
        }
    }

    private fun setData(dto: PhotographerRequestDomainDto) {
        binding.apply {
            etPhotographerInfo.setText(dto.introduction)
            categoryRVAdapter.setListData(dto.categories as ArrayList<CategoryDomainDto>)
            placeRVAdapter.setListData(dto.places as ArrayList<PlaceDomainDto>)
            setAccountAdapter(dto.account?.accountBank, dto.account?.accountNum)
        }
    }

    private fun setButtonEnable() {
        binding.btnUpload.apply {
            isEnabled = true
            setBackgroundResource(R.drawable.rectangle_blue400_radius_8)
        }
    }
    private fun setButtonDisable() {
        binding.btnUpload.apply {
            isEnabled = false
            setBackgroundResource(R.drawable.rectangle_gray400_radius_8)
        }
    }


    private fun getAccountBank() = binding.layoutPhotographerAccount.tvPhotographerAccount.text
    private fun getAccountNum() = binding.layoutPhotographerAccount.etPhotographerAccount.text

    private fun moveToPop() = findNavController().navigate(R.id.action_writePortfolioFragment_pop)

}