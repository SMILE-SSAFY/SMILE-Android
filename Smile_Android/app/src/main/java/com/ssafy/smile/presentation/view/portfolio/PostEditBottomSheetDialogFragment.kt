package com.ssafy.smile.presentation.view.portfolio

import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.CommonDialog
import com.ssafy.smile.databinding.FragmentPostEditBottomSheetDialogBinding
import com.ssafy.smile.domain.model.DialogBody
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseBottomSheetDialogFragment
import com.ssafy.smile.presentation.viewmodel.portfolio.PortfolioGraphViewModel

private const val TAG = "PostEditBottomSheetDialogFragment_싸피"
class PostEditBottomSheetDialogFragment : BaseBottomSheetDialogFragment<FragmentPostEditBottomSheetDialogBinding>(FragmentPostEditBottomSheetDialogBinding::inflate) {

    private val portfolioGraphViewModel: PortfolioGraphViewModel by navGraphViewModels(R.id.portfolioGraph)
    private val args: PostEditBottomSheetDialogFragmentArgs by navArgs()

    override fun initView() {
        setObserver()
    }

    private fun setObserver() {
        deletePostByIdResponseObserver()
    }

    override fun setEvent() {
        binding.apply {
            layoutModifyPost.setOnClickListener{
                val action = PostEditBottomSheetDialogFragmentDirections.actionPostEditBottomSheetDialogFragmentToWritePostFragment(args.postId, args.postDomainDto)
                findNavController().navigate(action)
            }
            layoutRemovePost.setOnClickListener {
                val dialog = CommonDialog(requireContext(), DialogBody("게시글을 삭제 하시겠습니까?\n(게시글 삭제시 복구할 수 없습니다)", "확인", "취소"), { portfolioGraphViewModel.deletePostById(args.postId) })
                showDialog(dialog, viewLifecycleOwner)
            }
        }
    }

    private fun deletePostByIdResponseObserver() {
        portfolioGraphViewModel.deletePostByIdResponse.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkUtils.NetworkResponse.Success -> {
                    showToast(requireContext(), "삭제되었습니다.", Types.ToastType.BASIC)
                    findNavController().navigate(R.id.action_postEditBottomSheetDialogFragment_pop)
                }
                is NetworkUtils.NetworkResponse.Failure -> {
                    showToast(requireContext(), "게시글 삭제 요청에 실패했습니다. 다시 시도해주세요.", Types.ToastType.WARNING)
                }
                is NetworkUtils.NetworkResponse.Loading -> {
                }
            }
        }
    }
}