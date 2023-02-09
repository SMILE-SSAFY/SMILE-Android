package com.ssafy.smile.presentation.view.mypage

import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ssafy.smile.R
import com.ssafy.smile.common.util.Constants.IMAGE_BASE_URL
import com.ssafy.smile.common.util.ImageUtils
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.util.PermissionUtils
import com.ssafy.smile.common.view.CommonDialog
import com.ssafy.smile.data.remote.model.ReviewResponseDto
import com.ssafy.smile.databinding.FragmentReviewDetailBinding
import com.ssafy.smile.domain.model.DialogBody
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.portfolio.ReviewDetailViewModel


class ReviewDetailFragment : BaseFragment<FragmentReviewDetailBinding>(FragmentReviewDetailBinding::bind, R.layout.fragment_review_detail) {
    private val navArgs : ReviewDetailFragmentArgs by navArgs()
    private val viewModel : ReviewDetailViewModel by viewModels()
    private lateinit var photographerName : String
    private var reservationId : Long = -1L
    private var reviewId : Long = -1L

    override fun initView() {
        photographerName = navArgs.photographerName?:""
        reservationId = navArgs.reservationId
        reviewId = navArgs.reviewId
        binding.tvPhotographerName.text = photographerName
        if (reviewId<0) setReviewWriteView()
        else viewModel.getReviewInfo(reviewId)
    }

    override fun setEvent() {
        viewModel.apply {
            imageDataResponse.observe(viewLifecycleOwner){
                if (it==null){
                    Glide.with(requireContext()).clear(binding.ivImage)
                    setImageViewVisibility(false)
                    setGalleryViewVisibility(true)
                }
                else{
                    Glide.with(requireContext()).load(it).into(binding.ivImage)
                    setImageViewVisibility(true)
                    setGalleryViewVisibility(false)
                }
            }
            reviewDataResponse.observe(viewLifecycleOwner){
                if (it) setButtonEnable()
                else setButtonDisable()
            }
            getReviewLiveData.observe(viewLifecycleOwner){
                when(it) {
                    is NetworkUtils.NetworkResponse.Loading -> { showLoadingDialog(requireContext()) }
                    is NetworkUtils.NetworkResponse.Success -> {
                        dismissLoadingDialog()
                        setReviewShowView(it.data)
                    }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        dismissLoadingDialog()
                        showToast(requireContext(), requireContext().getString(R.string.msg_common_error, "리뷰 조회"), Types.ToastType.ERROR)
                        moveToPopUpSelf()
                    }
                }
            }
            postReviewResponse.observe(viewLifecycleOwner){
                when(it){
                    is NetworkUtils.NetworkResponse.Loading -> {
                        showLoadingDialog(requireContext())
                    }
                    is NetworkUtils.NetworkResponse.Success -> {
                        dismissLoadingDialog()
                        showToast(requireContext(), "리뷰가 작성되었습니다.", Types.ToastType.SUCCESS)
                        moveToPopUpSelf()
                    }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        dismissLoadingDialog()
                        showToast(requireContext(), requireContext().getString(R.string.msg_common_error, "리뷰 등록"), Types.ToastType.ERROR)
                    }
                }
            }
            deleteReviewResponse.observe(viewLifecycleOwner){
                showToast(requireContext(), "리뷰가 삭제되었습니다.", Types.ToastType.SUCCESS)
                moveToPopUpSelf()
            }
        }
    }

    private fun setReviewWriteView() {
        initToolbar("리뷰 작성")
        binding.apply {
            ratingBar.setOnTouchListener { view, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_UP -> {
                        viewModel.uploadScoreData(binding.ratingBar.getReviewScore())
                        view.performClick()
                    }
                }
                false
            }
            btnPictureGallery.setOnClickListener {
                PermissionUtils.actionGalleryPermission(requireContext(), 1, "리뷰 사진은 한 장만 선택가능합니다.") {
                    viewModel.uploadImageData(ImageUtils.getFileFromUri(requireContext(), it[0]))
                }
            }
            btnImageClose.setOnClickListener {
                viewModel.uploadImageData(null)
            }
            etReviewInfo.addTextChangedListener {
                viewModel.uploadContentData(etReviewInfo.text.toString())
            }
            btnUpload.visibility = View.VISIBLE
            btnUpload.setOnClickListener {
                viewModel.postReviewInfo(reservationId)
            }
        }
    }

    private fun setReviewShowView(reviewDto: ReviewResponseDto){
        initToolbar("리뷰 조회")
        binding.apply {
            ratingBar.setIndicatorEnable(true)                  // TODO : ratingBar 클릭 막기
            setImageViewVisibility(true)
            setGalleryViewVisibility(false)
            tilPhotographerInfo.isEnabled = false
            btnImageClose.visibility = View.GONE
            btnUpload.visibility = View.GONE
            btnDelete.visibility = View.VISIBLE
            btnDelete.setOnClickListener { showReviewDeleteDialog() }
            ratingBar.setReviewScore(reviewDto.score)
            etReviewInfo.setText(reviewDto.content)
            Glide.with(requireContext()).load(IMAGE_BASE_URL + reviewDto.photoUrl).into(binding.ivImage)
        }
    }

    private fun setImageViewVisibility(isVisible : Boolean){
        if (isVisible) binding.layoutImage.visibility = View.VISIBLE
        else binding.layoutImage.visibility = View.GONE
    }

    private fun setGalleryViewVisibility(isVisible : Boolean){
        if (isVisible) binding.btnPictureGallery.visibility = View.VISIBLE
        else binding.btnPictureGallery.visibility = View.GONE
    }

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

    private fun initToolbar(toolbarTitle : String){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar(toolbarTitle, true) { moveToPopUpSelf() }
    }

    private fun showReviewDeleteDialog(){
        val dialog = CommonDialog(requireContext(), DialogBody(resources.getString(R.string.delete_review), "삭제"), { viewModel.deleteReviewInfo(reviewId) })
        showDialog(dialog, viewLifecycleOwner)
    }

    private fun moveToPopUpSelf() = findNavController().navigate(R.id.action_reviewDetailFragment_pop)

}