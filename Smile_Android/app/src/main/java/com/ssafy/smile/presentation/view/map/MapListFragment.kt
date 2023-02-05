package com.ssafy.smile.presentation.view.map

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.databinding.FragmentMapListBinding
import com.ssafy.smile.domain.model.PostSearchDomainDto
import com.ssafy.smile.domain.model.PostSearchRVDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.ClusterPostRVAdapter
import com.ssafy.smile.presentation.base.BaseBottomSheetDialogFragment
import com.ssafy.smile.presentation.viewmodel.map.MapViewModel


class MapListFragment : BaseBottomSheetDialogFragment<FragmentMapListBinding>(FragmentMapListBinding::inflate) {

    private val viewModel : MapViewModel by viewModels()
    private val navArgs: MapListFragmentArgs by navArgs()
    private var clusterId : Long = -1L

    private lateinit var clusterPostRvAdapter : ClusterPostRVAdapter
    private var searchType = Types.PostSearchType.HEART

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet!!).apply {
                state = BottomSheetBehavior.STATE_EXPANDED
                isDraggable = false
            }
            setupRatio(bottomSheet, 90)
        }
        return dialog
    }

    override fun initView() {
        clusterId = navArgs.clusterId
        setObserver()
        initRvAdapter()
    }

    override fun setEvent() {
        setRefreshLayoutEvent()
        setChipEvent()
    }

    private fun setObserver(){
        viewModel.apply {
            getPostSearchListResponse.observe(viewLifecycleOwner){
                when(it){
                    is NetworkUtils.NetworkResponse.Loading -> { }
                    is NetworkUtils.NetworkResponse.Success -> {
                        if (it.data.articleRedisList.isEmpty()){
                            setEmptyView(true)
                            setRVView(false)
                        }else{
                            setEmptyView(false)
                            val list = it.data.articleRedisList.map { postSearchDto -> (postSearchDto.makeToDomainDto()).makeToRVDto() }
                            setRVView(true, it.data.isEndPage, list as ArrayList<PostSearchRVDomainDto>)
                        }
                    }
                    is NetworkUtils.NetworkResponse.Failure -> {
                        showToast(requireContext(), requireContext().getString(com.ssafy.smile.R.string.msg_common_error, "게시글 목록을 가져오는"), Types.ToastType.ERROR)
                    }
                }
            }
            getPostPostSearchList(Types.PostSearchType.HEART, 0)
        }
    }


    private fun setRefreshLayoutEvent() {
        binding.apply {
            refreshLayout.setOnRefreshListener {
                chipGroup.children
                    .toList()
                    .filter { (it as Chip).isChecked }
                    .forEach {
                        clusterPostRvAdapter.page = 0
                        when((it as Chip).text){
                            "인기순" -> getPostPostSearchList(Types.PostSearchType.HEART, clusterPostRvAdapter.page)
                            "최신순" -> getPostPostSearchList(Types.PostSearchType.TIME, clusterPostRvAdapter.page)
                            "거리순" -> getPostPostSearchList(Types.PostSearchType.DISTANCE, clusterPostRvAdapter.page)
                            else -> getPostPostSearchList(Types.PostSearchType.HEART, clusterPostRvAdapter.page)
                        }
                    }
                refreshLayout.isRefreshing = false
            }
        }
    }

    private fun setChipEvent() {
        binding.apply {
            chipPopular.setOnClickListener {
                chipPopular.isEnabled = false
                chipCreated.isEnabled = true
                chipDistance.isEnabled = true
                searchType = Types.PostSearchType.HEART
                clusterPostRvAdapter.page = 0
                getPostPostSearchList(searchType, 0)
            }
            chipCreated.setOnClickListener {
                chipPopular.isEnabled = true
                chipCreated.isEnabled = false
                chipDistance.isEnabled = true
                searchType = Types.PostSearchType.TIME
                clusterPostRvAdapter.page = 0
                getPostPostSearchList(searchType, 0)
            }
            chipDistance.setOnClickListener {
                chipPopular.isEnabled = true
                chipCreated.isEnabled = true
                chipDistance.isEnabled = false
                searchType = Types.PostSearchType.DISTANCE
                clusterPostRvAdapter.page = 0
                getPostPostSearchList(searchType, 0)
            }
        }
    }

    private fun initRvAdapter(){        // TODO : 서버에서 삭제되었을 경우, 예외 처리 + bottomFragment 생명주기 관리(fragment saveInstanceState)
        clusterPostRvAdapter = ClusterPostRVAdapter().apply {
            setItemClickListener(object : ClusterPostRVAdapter.ItemClickListener{
                override fun onClickItem(view: View, position: Int, postSearchDto: PostSearchDomainDto) {
                    val action = MapListFragmentDirections.actionMapListFragmentToPortfolioGraph(postSearchDto.id)
                    findNavController().navigate(action)
                }
            })
        }
        binding.rvPost.apply {
            adapter = clusterPostRvAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                    val itemTotalCount = recyclerView.adapter!!.itemCount-1

                    if (!binding.rvPost.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount && !clusterPostRvAdapter.isEnd) {
                        clusterPostRvAdapter.page += 1
                        clusterPostRvAdapter.dismissProgress()
                        getPostPostSearchList(searchType, clusterPostRvAdapter.page)
                    }
                }
            })
        }
    }

    private fun setEmptyView(isSet : Boolean){
        if (isSet){
            binding.layoutEmptyView.layoutEmptyView.visibility = View.VISIBLE
            binding.layoutEmptyView.tvEmptyView.text = "열람 가능한 게시글이 존재하지 않습니다."
        }else binding.layoutEmptyView.layoutEmptyView.visibility = View.GONE
    }

    private fun setRVView(isSet : Boolean, isEnd : Boolean=true, itemList : ArrayList<PostSearchRVDomainDto> = arrayListOf()){
        if (isSet) {
            binding.layoutRvPost.visibility = View.VISIBLE
            clusterPostRvAdapter.setListData(searchType, isEnd, itemList)
        } else binding.layoutRvPost.visibility = View.GONE
    }

    private fun getPostPostSearchList(type: Types.PostSearchType, page: Int) = viewModel.getPostPostSearchList(clusterId, type.getValue(), page)


}