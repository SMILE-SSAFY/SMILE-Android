package com.ssafy.smile.presentation.view.mypage

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.smile.R
import com.ssafy.smile.common.util.ImageUtils
import com.ssafy.smile.common.util.PermissionUtils.actionGalleryPermission
import com.ssafy.smile.common.view.LoadingDialog
import com.ssafy.smile.databinding.FragmentPhotographerProfileBinding
import com.ssafy.smile.presentation.base.BaseBottomSheetDialogFragment
import com.ssafy.smile.presentation.viewmodel.mypage.PhotographerWriteGraphViewModel


class PhotographerProfileFragment() : BaseBottomSheetDialogFragment<FragmentPhotographerProfileBinding>(FragmentPhotographerProfileBinding::inflate) {
    private val viewModel : PhotographerWriteGraphViewModel by navGraphViewModels(R.id.registerPortFolioGraph)

    override fun initView() {

    }

    override fun setEvent() {
        setClickListener()
    }

    private fun setClickListener(){
        binding.apply {
            layoutSelectImageGallery.setOnClickListener {
                actionGalleryPermission(requireContext(), 1, "프로필 사진은 한 장만 선택가능합니다."){
                    viewModel.profileBitmap = ImageUtils.resizeImage(requireContext(), it[0])
                    findNavController().navigate(R.id.photographerProfileCropFragment)
                }
            }
            layoutRemoveImage.setOnClickListener {
                viewModel.uploadProfileImage(null)
                findNavController().navigate(R.id.action_photographerProfileFragment_pop)
            }
        }
    }
}