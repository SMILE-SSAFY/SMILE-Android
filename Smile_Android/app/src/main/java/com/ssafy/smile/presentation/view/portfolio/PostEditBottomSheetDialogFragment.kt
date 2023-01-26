package com.ssafy.smile.presentation.view.portfolio

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.CommonDialog
import com.ssafy.smile.common.view.LoadingDialog
import com.ssafy.smile.databinding.FragmentPostEditBottomSheetDialogBinding
import com.ssafy.smile.domain.model.DialogBody
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseBottomSheetDialogFragment
import com.ssafy.smile.presentation.viewmodel.portfolio.PostViewModel

private const val TAG = "PostEditBottomSheetDialogFragment_싸피"
class PostEditBottomSheetDialogFragment : BaseBottomSheetDialogFragment<FragmentPostEditBottomSheetDialogBinding>(FragmentPostEditBottomSheetDialogBinding::inflate) {

    private val postViewModel by activityViewModels<PostViewModel>()
    private val args: PostDetailFragmentArgs by navArgs()

    override fun initView() {
        setObserver()
    }

    private fun setObserver() {
        deletePostByIdResponseObserver()
    }

    override fun setEvent() {
        binding.apply {
            layoutEdit.setOnClickListener{
                // TODO : 편집 화면으로 이동
            }
            layoutDelete.setOnClickListener {
                val dialog = CommonDialog(requireContext(), DialogBody("게시글을 삭제 하시겠습니까?\n(게시글 삭제시 복구할 수 없습니다)", "확인", "취소"),
                    { postViewModel.deletePostById(args.postId) })
                showDialog(dialog, viewLifecycleOwner)
            }
        }
    }

    private fun deletePostByIdResponseObserver() {
        postViewModel.deletePostByIdResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Success -> {
                    dismissLoadingDialog()
                    showToast(requireContext(), "삭제되었습니다.", Types.ToastType.BASIC)
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    dismissLoadingDialog()
                    Log.d(TAG, "deletePostByIdResponseObserver: ${it.errorCode}")
                    showToast(requireContext(), "게시글 삭제 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
                is NetworkUtils.NetworkResponse.Loading -> {
                    showLoadingDialog(requireContext())
                }
            }
        }
    }

    override var mLoadingDialog: LoadingDialog
        get() = LoadingDialog(requireContext())
        set(value) {}
}