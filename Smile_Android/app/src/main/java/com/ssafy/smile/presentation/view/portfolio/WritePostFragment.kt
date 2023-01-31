package com.ssafy.smile.presentation.view.portfolio

import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.R
import com.ssafy.smile.common.util.ImageUtils
import com.ssafy.smile.common.util.NetworkUtils.NetworkResponse
import com.ssafy.smile.common.util.PermissionUtils.actionGalleryPermission
import com.ssafy.smile.databinding.FragmentWritePostBinding
import com.ssafy.smile.domain.model.AddressDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.ImageRVAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.portfolio.WritePostViewModel
import java.io.File
import kotlin.math.abs

class WritePostFragment : BaseFragment<FragmentWritePostBinding>(FragmentWritePostBinding::bind, R.layout.fragment_write_post) {
    private val viewModel : WritePostViewModel by viewModels()

    private lateinit var imageRvAdapter: ImageRVAdapter
    private val spinnerAdapter: ArrayAdapter<String> by lazy {
        val items = resources.getStringArray(R.array.spinner_category)
        ArrayAdapter(requireContext(), R.layout.item_spinner, items)
    }

    override fun initView() {
        initToolbar()
        setRvAdapter()
        setSpinnerAdapter()
        setObserver()
        setButtonDisable()
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("게시글 업로드", true)
    }

    override fun setEvent() {
        setClickListener()
    }

    private fun setRvAdapter(){
        imageRvAdapter = ImageRVAdapter(requireContext()).apply {
            setItemClickListener(object : ImageRVAdapter.ItemClickListener{
                override fun onClickBtnDelete(view: View, position: Int, dto: File) {
                    deleteItem(position)
                    viewModel.uploadImageData(getImageData())
                }
            })
        }
        binding.rvPictureContent.apply {
            adapter = imageRvAdapter
        }
    }

    private fun setSpinnerAdapter() {
        binding.apply {
            tvCategoryContent.setAdapter(spinnerAdapter)
            tvCategoryContent.setOnItemClickListener { _, _, _, _ ->
                viewModel.uploadCategoryData(getCategoryData())
            }
        }
    }
    private fun setObserver(){
        viewModel.run {
            postDataResponse.observe(viewLifecycleOwner){
                when (it){
                    true -> setButtonEnable()
                    false -> setButtonDisable()
                }
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
        }
    }
    private fun setClickListener(){
        binding.apply {
            btnPictureContent.setOnClickListener {
                actionGalleryPermission(requireContext(), abs(3-imageRvAdapter.itemCount), "최대 ${abs(3-imageRvAdapter.itemCount)}장까지 선택 가능합니다."){
                    val fileList = it.map { uri -> ImageUtils.getImageFileFromUri(requireContext(), uri) }
                    imageRvAdapter.setListData(ArrayList(fileList))
                    viewModel.uploadImageData(getImageData())
                }
            }

            btnSearch.setOnClickListener {
                val tempAddress = getAddressData()
                viewModel.uploadAddressData(tempAddress)
            }
            tvCategoryContent.setOnItemClickListener { _, _, _, _ ->
                viewModel.uploadCategoryData(tvCategoryContent.text.toString())
            }
            btnUpload.setOnClickListener {
                viewModel.uploadPost()
            }
        }
    }

    private fun getImageData() = imageRvAdapter.getListData()
    private fun getAddressData() = AddressDomainDto("임시 주소", 0.0, 0.0) // TODO : 주소록 연결
    private fun getCategoryData() = binding.tvCategoryContent.text.toString()

    private fun setButtonEnable() {
        binding.btnUpload.apply {
            isClickable = true
            setBackgroundResource(R.drawable.rectangle_blue400_radius_8)
        }
    }
    private fun setButtonDisable() {
        binding.btnUpload.apply {
            isClickable = false
            setBackgroundResource(R.drawable.rectangle_gray400_radius_8)
        }
    }

}