package com.ssafy.smile.presentation.view.mypage

import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.ssafy.smile.R
import com.ssafy.smile.common.util.ImageUtils
import com.ssafy.smile.common.util.PermissionUtils.actionGalleryPermission
import com.ssafy.smile.common.view.LoadingDialog
import com.ssafy.smile.databinding.FragmentPhotographerProfileBinding
import com.ssafy.smile.presentation.base.BaseBottomSheetDialogFragment
import com.ssafy.smile.presentation.viewmodel.mypage.PhotographerWriteGraphViewModel


class PhotographerProfileFragment(override var mLoadingDialog: LoadingDialog) : BaseBottomSheetDialogFragment<FragmentPhotographerProfileBinding>(FragmentPhotographerProfileBinding::inflate) {
    private val viewModel : PhotographerWriteGraphViewModel by navGraphViewModels(R.id.registerPortFolioGraph)
    override fun initView() {

    }

    override fun setEvent() {
        setClickListener()
    }

    private fun setClickListener(){
        binding.apply {
            layoutSelectImagePost.setOnClickListener {  // TODO : 추가 - 작가의 포트폴리오 페이지랑 연결 (=게시글 사진 선택하기)

            }
            layoutSelectImageGallery.setOnClickListener {
                actionGalleryPermission(requireContext(), 1, "프로필 사진은 한 장만 선택가능합니다."){
                    val profileBitmap = ImageUtils.resizeImage(requireContext(), it[0])
                    viewModel.profileBitmap = profileBitmap
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