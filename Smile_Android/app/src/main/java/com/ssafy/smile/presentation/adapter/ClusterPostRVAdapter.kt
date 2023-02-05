package com.ssafy.smile.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smile.R
import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.databinding.ItemRvAddressBinding
import com.ssafy.smile.databinding.ItemRvClusterPostBinding
import com.ssafy.smile.domain.model.AddressDomainDto
import com.ssafy.smile.domain.model.PostSearchDomainDto
import com.ssafy.smile.domain.model.Types

class ClusterPostRVAdapter() : RecyclerView.Adapter<ClusterPostRVAdapter.Holder>() {

    private val itemList : ArrayList<PostSearchDomainDto> = arrayListOf()
    private var listType : Types.PostSearchType = Types.PostSearchType.HEART

    fun setListData(type : Types.PostSearchType, dataList: ArrayList<PostSearchDomainDto>){
        itemList.clear()
        itemList.addAll(dataList)
        listType = type
        notifyDataSetChanged()
    }


    inner class Holder(private val binding: ItemRvClusterPostBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindInfo(position: Int, postSearchDto: PostSearchDomainDto){
            binding.apply {
                tvCategory.text = postSearchDto.category
                tvCreatedAt.text = CommonUtils.getDiffTime(postSearchDto.createdAt)
                tvDistance.text = CommonUtils.getDiffDistance(postSearchDto.distance)
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
            itemView.setOnClickListener { itemClickListener.onClickItem(it, position, itemList[position]) }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return  Holder(ItemRvClusterPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val dto = itemList[position]
        holder.apply {
            bindInfo(position, dto)
            itemView.tag = dto
        }
    }

    override fun getItemCount(): Int = itemList.size

    interface ItemClickListener{
        fun onClickItem(view: View, position: Int, postSearchDto: PostSearchDomainDto)
    }

    private lateinit var itemClickListener: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener){ this.itemClickListener = itemClickListener }
}