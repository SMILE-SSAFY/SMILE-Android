package com.ssafy.smile.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.common.util.Constants
import com.ssafy.smile.data.remote.model.PostHeartDto
import com.ssafy.smile.databinding.ItemRvClusterPostBinding
import com.ssafy.smile.databinding.ItemRvClusterProgressBinding
import com.ssafy.smile.domain.model.PostSearchDomainDto
import com.ssafy.smile.domain.model.PostSearchRVDomainDto
import com.ssafy.smile.domain.model.Types

class ClusterPostRVAdapter() : RecyclerView.Adapter<ViewHolder>() {
    companion object{
        private const val PAGING_NUM = 9
        private const val VIEW_TYPE_CONTENT = 0
        private const val VIEW_TYPE_PROGRESS = 1
    }

    private val itemList : ArrayList<PostSearchRVDomainDto> = arrayListOf()
    private var listType : Types.PostSearchType = Types.PostSearchType.HEART
    var isEnd : Boolean = false
    var page : Int = 0

    fun changeDataHearts(heartDto : PostHeartDto){
        val item = itemList.find{ it.postSearchDto?.articleId == heartDto.articleId }
        val itemIndex = itemList.indexOf(item)
        item?.postSearchDto?.let {
            it.hearts = heartDto.hearts
            it.isHeart = heartDto.isHeart
        }
        notifyItemChanged(itemIndex)
    }

    fun setListData(type : Types.PostSearchType, isEnd : Boolean, dataList: ArrayList<PostSearchRVDomainDto>){
        this.isEnd = isEnd
        if (page==0){
            listType = type
            itemList.clear()
            itemList.addAll(dataList)
            if (!isEnd) itemList.add(PostSearchRVDomainDto(Types.PagingRVType.PROGRESS, null))
            notifyDataSetChanged()
        }else{
            itemList.addAll(dataList)
            if (!isEnd) itemList.add(PostSearchRVDomainDto(Types.PagingRVType.PROGRESS, null))
            notifyDataSetChanged()
        }
    }

    fun dismissProgress(){ itemList.removeAt(itemList.lastIndex) }

    override fun getItemViewType(position: Int): Int {
        return when(itemList[position].type){
            Types.PagingRVType.CONTENT -> VIEW_TYPE_CONTENT
            Types.PagingRVType.PROGRESS -> VIEW_TYPE_PROGRESS
        }
    }


    inner class Holder(private val binding: ItemRvClusterPostBinding) : ViewHolder(binding.root){
        fun bindInfo(view: View, position: Int, postSearchDto: PostSearchDomainDto){
            binding.apply {
                Glide.with(view.context)
                    .load(Constants.IMAGE_BASE_URL + postSearchDto.photoUrl)
                    .into(ivImage)
                tvCategory.text = postSearchDto.category
                tvCreatedAt.text = CommonUtils.stringToDate(postSearchDto.createdAt)?.let { CommonUtils.getDiffTime(it) }
                tvDistance.text = CommonUtils.getDiffDistance(postSearchDto.distance)
                ctvLike.isChecked = postSearchDto.isHeart
                tvLike.text = postSearchDto.hearts.toString()
                tvName.text = postSearchDto.photographerName
                tvLocation.text = postSearchDto.detailAddress

                when (listType) {
                    Types.PostSearchType.HEART -> {
                        showCreatedAtVisibility(false)
                        showDistanceVisibility(false)
                    }
                    Types.PostSearchType.TIME -> {
                        showCreatedAtVisibility(true)
                        showDistanceVisibility(false)
                    }
                    else -> {
                        showCreatedAtVisibility(false)
                        showDistanceVisibility(true)
                    }
                }
            }
            binding.ctvLike.setOnClickListener { itemClickListener.onClickHeart(binding.tvLike, it as CheckedTextView, position, postSearchDto) }
            itemView.setOnClickListener { itemClickListener.onClickItem(it, position, postSearchDto) }
        }
        private fun showCreatedAtVisibility(isVisible : Boolean){
            if (isVisible) binding.tvCreatedAt.visibility = View.VISIBLE
            else binding.tvCreatedAt.visibility = View.GONE
        }
        private fun showDistanceVisibility(isVisible : Boolean){
            if (isVisible) binding.tvDistance.visibility = View.VISIBLE
            else binding.tvDistance.visibility = View.GONE
        }
    }

    inner class ProgressHolder(private val binding: ItemRvClusterProgressBinding) : ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CONTENT -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRvClusterPostBinding.inflate(layoutInflater, parent, false)
                Holder(binding)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRvClusterProgressBinding.inflate(layoutInflater, parent, false)
                ProgressHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dto = itemList[position]
        if(holder is Holder){
            dto.postSearchDto?.let { holder.bindInfo(holder.itemView, position, it) }
            holder.itemView.tag = dto
        }
    }

    override fun getItemCount(): Int = itemList.size


    interface ItemClickListener{
        fun onClickItem(view: View, position: Int, postSearchDto: PostSearchDomainDto)
        fun onClickHeart(tvView : TextView, checkedView: CheckedTextView, position: Int, postSearchDto: PostSearchDomainDto)
    }

    private lateinit var itemClickListener: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener){ this.itemClickListener = itemClickListener }
}