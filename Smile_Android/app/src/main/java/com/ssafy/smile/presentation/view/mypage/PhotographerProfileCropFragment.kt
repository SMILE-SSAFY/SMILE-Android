package com.ssafy.smile.presentation.view.mypage

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.ssafy.smile.R
import com.ssafy.smile.common.util.ImageUtils
import com.ssafy.smile.common.util.ImageUtils.convertBitmapToFile
import com.ssafy.smile.databinding.FragmentImageCropBinding
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.mypage.PhotographerWriteViewModel

class PhotographerProfileCropFragment : BaseFragment<FragmentImageCropBinding>(
    FragmentImageCropBinding::bind, R.layout.fragment_image_crop
) {
    private val viewModel : PhotographerWriteViewModel by navGraphViewModels(R.id.registerPortFolioGraph)

    override fun initView() {
        initToolbar()
        val image = viewModel.profileBitmap
        if(image == null){
            showToast(requireContext(), "이미지를 가져올 수 없습니다.", Types.ToastType.ERROR)
            moveToPopUpSelf()
        }
        binding.cropImageView.setImageBitmap(image)
    }

    override fun setEvent() {
        binding.btnSave.setOnClickListener {
            val croppedImgBitmap = binding.cropImageView.getCroppedImage(500,500)
            val croppedImgFile = croppedImgBitmap?.convertBitmapToFile(context = requireContext())
            viewModel.uploadProfileImage(croppedImgFile)
            moveToWritePortfolio()
        }
    }


    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("사진 편집", true) { moveToPopUpSelf() }
    }

    private fun moveToPopUpSelf() = findNavController().navigate(R.id.action_photographerProfileCropFragment_pop)
    private fun moveToWritePortfolio() = findNavController().navigate(R.id.action_photographerProfileCropFragment_to_writePortfolioFragment)

}