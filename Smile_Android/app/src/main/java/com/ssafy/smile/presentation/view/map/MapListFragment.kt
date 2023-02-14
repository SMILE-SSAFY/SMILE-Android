package com.ssafy.smile.presentation.view.map

import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.CheckedTextView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.ssafy.smile.R
import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.databinding.FragmentMapListBinding
import com.ssafy.smile.domain.model.PostSearchDomainDto
import com.ssafy.smile.domain.model.PostSearchRVDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.ClusterPostRVAdapter
import com.ssafy.smile.presentation.base.BaseBottomSheetDialogFragment
import com.ssafy.smile.presentation.viewmodel.map.MapViewModel


// TODO : 페이징 처리 해결 -> 정 안되면, paging Library 3으로 변경 고려.
class MapListFragment : BaseBottomSheetDialogFragment<FragmentMapListBinding>(FragmentMapListBinding::inflate) {

    private val viewModel : MapViewModel by viewModels()
    private val navArgs: MapListFragmentArgs by navArgs()
    private lateinit var clusterPostRvAdapter : ClusterPostRVAdapter

    private var clusterId : Long = -1L
    private var searchType = Types.PostSearchType.HEART
    private var latestScrolledPosition : Int = 0

    override fun initView() {
        setObserver()
        initToolbar()
        initRvAdapter()
        setRefreshLayoutEvent()
        setChipEvent()
        setDataState()
    }

    override fun setEvent() { }

    private fun setDataState(){
        viewModel.apply {
            viewModel.uploadClusterIdData(navArgs.clusterId)
            val data = viewModel.getData()
            clusterId = data.clusterId
            searchType = data.searchType
            clusterPostRvAdapter.page = data.adapterPageNum
            latestScrolledPosition = data.scrolledPositionNum
            getPostPostSearchList(searchType, clusterPostRvAdapter.page)
        }
    }
    private fun setObserver(){
        viewModel.apply {
            getPostSearchListResponse.observe(viewLifecycleOwner){
                when(it){
                    is NetworkUtils.NetworkResponse.Loading -> { }
                    is NetworkUtils.NetworkResponse.Success -> {
                        Log.d("스마일", "setObserver: ${it.data}")
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
            getUpdateHeartResponse.observe(viewLifecycleOwner){
                when(it){
                    is NetworkUtils.NetworkResponse.Loading -> {}
                    is NetworkUtils.NetworkResponse.Success -> clusterPostRvAdapter.changeDataHearts(it.data)
                    is NetworkUtils.NetworkResponse.Failure -> showToast(requireContext(), requireContext().getString(R.string.msg_common_error, "좋아요 과정 중"), Types.ToastType.ERROR)

                }
            }
        }
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("게시글 목록", true) { moveToPop() }
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
            latestScrolledPosition = 0
            viewModel.uploadScrolledPosition(latestScrolledPosition)
        }
    }

    private fun initRvAdapter(){
        clusterPostRvAdapter = ClusterPostRVAdapter().apply {
            setItemClickListener(object : ClusterPostRVAdapter.ItemClickListener{
                override fun onClickHeart(tvView: TextView, checkedView: CheckedTextView, position: Int, postSearchDto: PostSearchDomainDto) {
                    viewModel.updatePostHeart(postSearchDto.articleId)
                }
                override fun onClickItem(view: View, position: Int, postSearchDto: PostSearchDomainDto) {
                    val action = MapListFragmentDirections.actionMapListFragmentToPortfolioGraph(postId = postSearchDto.articleId, photographerId = postSearchDto.photographerId, goToDetail = true)
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
                    latestScrolledPosition = lastVisibleItemPosition
                    viewModel.uploadScrolledPosition(latestScrolledPosition)

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
//            if (latestScrolledPosition!=0) binding.rvPost.scrollToPosition(latestScrolledPosition)
        } else binding.layoutRvPost.visibility = View.GONE
    }

    private fun getPostPostSearchList(type: Types.PostSearchType, page: Int) = viewModel.getPostPostSearchList(clusterId, type.getValue(), page)
    private fun moveToPop() = findNavController().navigate(R.id.action_mapListFragment_pop)


}
