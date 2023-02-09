package com.ssafy.smile.presentation.view.portfolio

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.ssafy.smile.R
import com.ssafy.smile.common.util.AddressUtils
import com.ssafy.smile.common.util.Constants.IMAGE_BASE_URL
import com.ssafy.smile.common.util.ImageUtils
import com.ssafy.smile.common.util.ImageUtils.convertBitmapToFile
import com.ssafy.smile.common.util.NetworkUtils.NetworkResponse
import com.ssafy.smile.common.util.PermissionUtils.actionGalleryPermission
import com.ssafy.smile.databinding.FragmentWritePostBinding
import com.ssafy.smile.domain.model.AddressDomainDto
import com.ssafy.smile.domain.model.PostDomainDto
import com.ssafy.smile.domain.model.PostDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.ImageRVAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.portfolio.PortfolioGraphViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL
import kotlin.math.abs

class WritePostFragment : BaseFragment<FragmentWritePostBinding>(FragmentWritePostBinding::bind, R.layout.fragment_write_post) {

    private val navArgs : WritePostFragmentArgs by navArgs()
    private val portfolioGraphViewModel: PortfolioGraphViewModel by navGraphViewModels(R.id.portfolioGraph)

    private lateinit var imageRvAdapter: ImageRVAdapter

    override fun initView() {
        setRvAdapter()
        setSpinnerAdapter()
        setObserver()
        if (navArgs.postDomainDto!=null) setModifyPostView(navArgs.postDomainDto!!)
        else setWritePostView()
    }


    override fun setEvent() {
        setClickListener()
    }

    private fun setRvAdapter(){
        imageRvAdapter = ImageRVAdapter(requireContext()).apply {
            setItemClickListener(object : ImageRVAdapter.ItemClickListener{
                override fun onClickBtnDelete(view: View, position: Int, dto: File) {
                    deleteItem(position)
                    portfolioGraphViewModel.uploadImageData(getImageData())
                }
            })
        }
        binding.rvPictureContent.apply {
            adapter = imageRvAdapter
        }
    }

    private fun setSpinnerAdapter(text:String?=null) {
        binding.apply {
            tvCategoryContent.setText(text)
            tvCategoryContent.setAdapter(getSpinnerAdapter(requireContext()))
            tvCategoryContent.setOnItemClickListener { _, _, _, _ ->
                portfolioGraphViewModel.uploadCategoryData(getCategoryData())
            }
        }
    }
    private fun setObserver(){
        requireActivity().supportFragmentManager.setFragmentResultListener("getAddress",viewLifecycleOwner) { requestKey, result ->
            if (requestKey == "getAddress" && result["addressDomainDto"] != null) {
                val addressDomainDto = result["addressDomainDto"] as AddressDomainDto
                portfolioGraphViewModel.uploadAddressData(addressDomainDto)
                setAddressData(addressDomainDto)
            }
        }
        portfolioGraphViewModel.run {
            checkDataResponse.observe(viewLifecycleOwner){
                if (it) setButtonEnable()
                else setButtonDisable()
            }
            postDataResponse.observe(viewLifecycleOwner){
                setData(it)
            }
            postUploadResponse.observe(viewLifecycleOwner){
                when(it){
                    is NetworkResponse.Loading -> {
                        showLoadingDialog(requireContext())
                    }
                    is NetworkResponse.Success -> {
                        dismissLoadingDialog()
                        showToast(requireContext(), "게시글이 정상적으로 등록되었습니다.", Types.ToastType.SUCCESS, true)
                        findNavController().navigate(R.id.action_writePostFragment_pop)
                    }
                    is NetworkResponse.Failure -> {
                        dismissLoadingDialog()
                        showToast(requireContext(), "게시글 등록에 실패했습니다. 잠시 후 시도해주세요.", Types.ToastType.ERROR, true)
                    }
                }
            }
            postModifyResponse.observe(viewLifecycleOwner){
                when(it){
                    is NetworkResponse.Loading -> {
                        showLoadingDialog(requireContext())
                    }
                    is NetworkResponse.Success -> {
                        dismissLoadingDialog()
                        showToast(requireContext(), "게시글이 수정되었습니다.", Types.ToastType.SUCCESS, true)
                        findNavController().navigate(R.id.action_writePostFragment_pop)
                    }
                    is NetworkResponse.Failure -> {
                        dismissLoadingDialog()
                        showToast(requireContext(), "게시글 수정에 실패했습니다. 잠시 후 시도해주세요.", Types.ToastType.ERROR, true)
                    }
                }
            }
        }
    }



    private fun setClickListener(){
        binding.apply {
            btnPictureContent.setOnClickListener {
                actionGalleryPermission(requireContext(), abs(3-imageRvAdapter.itemCount), "최대 ${abs(3-imageRvAdapter.itemCount)}장까지 선택 가능합니다."){
                    val fileList = it.map { uri -> ImageUtils.getFileFromUri(requireContext(), uri) }
                    imageRvAdapter.setListData(fileList as ArrayList<File>)
                    portfolioGraphViewModel.uploadImageData(getImageData())
                }
            }

            btnSearchAddress.setOnClickListener {
                moveToAddressGraph()
            }
            tvCategoryContent.setOnItemClickListener { _, _, _, _ ->
                portfolioGraphViewModel.uploadCategoryData(tvCategoryContent.text.toString())
            }

            btnUpload.setOnClickListener {
                if (navArgs.postDomainDto==null) portfolioGraphViewModel.uploadPost()
                else portfolioGraphViewModel.modifyPost(navArgs.postId)
            }

        }
    }

    private fun setWritePostView(){
        initToolbar("게시글 업로드")
        setButtonDisable()
    }

    private fun setModifyPostView(postDomainDto: PostDomainDto){
        initToolbar("게시글 수정")
        binding.apply {
            btnUpload.text = "수정하기"
            val addressDomainDto = AddressDomainDto().apply { address = postDomainDto.detailAddress }.also {
                AddressUtils.getPointsFromGeo(requireContext(), postDomainDto.detailAddress)?.let {latLng ->
                    it.latitude = latLng.latitude
                    it.longitude = latLng.longitude
                }
            }
            lifecycleScope.launch(Dispatchers.IO) {
                val imageList = postDomainDto.photoUrl.map {
                    BitmapFactory.decodeStream(URL(IMAGE_BASE_URL+it).openConnection().getInputStream()).convertBitmapToFile(context = requireContext())!!
                }
                portfolioGraphViewModel.uploadData(imageList, addressDomainDto, postDomainDto.category.replace("#",""))
            }
        }
    }

    private fun initToolbar(msg:String){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar(msg, true){
            moveToPopUpSelf()
        }
    }

    private fun setData(postDto: PostDto) {
        binding.apply {
            imageRvAdapter.setListData(postDto.images as ArrayList<File>)
            postDto.addressDomainDto?.let { setAddressData(it) }
            setSpinnerAdapter(postDto.category)
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

    private fun getImageData() = imageRvAdapter.getListData()
    private fun getCategoryData() = binding.tvCategoryContent.text.toString()
    private fun getSpinnerAdapter(context: Context) =  ArrayAdapter(context, R.layout.item_spinner, resources.getStringArray(R.array.spinner_category))
    private fun setAddressData(addressDomainDto: AddressDomainDto) { binding.etPlaceContent.setText(addressDomainDto.address) }

    private fun moveToAddressGraph() = findNavController().navigate(R.id.action_writePostFragment_to_addressGraph)
    private fun moveToPopUpSelf() = findNavController().navigate(R.id.action_writePostFragment_pop)

}