package com.ssafy.smile.presentation.view.mypage.myInterest

import android.view.View
import android.widget.CheckedTextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.databinding.FragmentMyInterestArticleBinding
import com.ssafy.smile.domain.model.CustomPostDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.MyInterestArticleRVAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.mypage.MyInterestViewModel


class MyInterestArticleFragment : BaseFragment<FragmentMyInterestArticleBinding>(FragmentMyInterestArticleBinding::bind, R.layout.fragment_my_interest_article) {
    private val viewModel : MyInterestViewModel by viewModels()
    private lateinit var rvMyInterestArticleAdapter : MyInterestArticleRVAdapter


    override fun initView() {
        initRVAdapter()
    }

    override fun setEvent() {
        setObserver()
        getInterestInfo()
    }

    private fun setObserver(){
        viewModel.apply {
            getArticleHeartListResponse.observe(viewLifecycleOwner){
                when(it) {
                    is NetworkUtils.NetworkResponse.Loading -> {
                        showLoadingDialog(requireContext())
                    }
                    is NetworkUtils.NetworkResponse.Success -> {
                        dismissLoadingDialog()
                        if (it.data.isEmpty()){
                            setEmptyView(true)
                            setRVView(false)
                        }else{
                            val list = it.data.map { dto -> dto.toCustomPostDomainDto() }
                            setEmptyView(false)
                            setRVView(true, list as ArrayList<CustomPostDomainDto>)
                        }
                    }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        dismissLoadingDialog()
                        showToast(requireContext(), requireContext().getString(R.string.msg_common_error, "관심 게시글을 불러오는"), Types.ToastType.ERROR)
                    }
                }
            }
            postArticleHeartResponse.observe(viewLifecycleOwner){
                when(it) {
                    is NetworkUtils.NetworkResponse.Loading -> { }
                    is NetworkUtils.NetworkResponse.Success -> { getInterestInfo() }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        showToast(requireContext(), requireContext().getString(R.string.msg_common_error, "찜 설정"), Types.ToastType.ERROR)
                    }
                }
            }
        }
    }

    private fun getInterestInfo(){
        viewModel.getArticleHeartList()
    }

    private fun initRVAdapter(){
        rvMyInterestArticleAdapter = MyInterestArticleRVAdapter().apply {
            setItemClickListener(object : MyInterestArticleRVAdapter.ItemClickListener{
                override fun onClickItem(view: View, position: Int, articleDto: CustomPostDomainDto) {
                    moveToPortfolioGraph(articleDto)
                }
                override fun onClickHeart(view: CheckedTextView, position: Int, articleDto: CustomPostDomainDto) {
                    view.isChecked = !(view.isChecked)
                    viewModel.postArticleHeart(articleDto.articleId)
                }
            })
        }
        binding.recycleView.adapter = rvMyInterestArticleAdapter
    }

    private fun setEmptyView(isSet : Boolean){
        if (isSet){
            binding.layoutEmptyView.layoutEmptyView.visibility = View.VISIBLE
            binding.layoutEmptyView.tvEmptyView.text = getString(R.string.tv_interest_article_empty)
        }else binding.layoutEmptyView.layoutEmptyView.visibility = View.GONE
    }

    private fun setRVView(isSet : Boolean, itemList : ArrayList<CustomPostDomainDto> = arrayListOf()){
        if (isSet) {
            binding.recycleView.visibility = View.VISIBLE
            rvMyInterestArticleAdapter.setListData(itemList)
        } else binding.recycleView.visibility = View.GONE
    }

    private fun moveToPortfolioGraph(articleDto : CustomPostDomainDto){
        val action = MyInterestFragmentDirections.actionMyInterestFragmentToPortfolioGraph(-1L, articleDto.articleId)
        findNavController().navigate(action)
    }


}