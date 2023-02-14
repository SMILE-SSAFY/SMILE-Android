package com.ssafy.smile.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.common.util.Constants
import com.ssafy.smile.databinding.ItemRvReviewBinding
import com.ssafy.smile.domain.model.PhotographerReviewDomainDto

class PhotographerReviewRVAdapter() : RecyclerView.Adapter<PhotographerReviewRVAdapter.Holder>() {

    private val itemList : ArrayList<PhotographerReviewDomainDto> = arrayListOf()

    fun setListData(dataList: ArrayList<PhotographerReviewDomainDto>){
        itemList.clear()
        itemList.addAll(dataList)
        notifyDataSetChanged()
    }

    inner class Holder(private val binding: ItemRvReviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindInfo(itemView : View, position: Int, reviewDto: PhotographerReviewDomainDto){
            binding.apply {
                tvUserName.text = reviewDto.userName
                ratingBar.rating = reviewDto.score
                tvCreatedAt.text = CommonUtils.stringToDate(reviewDto.createdAt)?.let { CommonUtils.getDiffTime(it) }
                Glide.with(itemView.context).load(Constants.IMAGE_BASE_URL + reviewDto.photoUrl).into(ivImage)
                tvContent.text = System.getProperty("line.separator")?.let { reviewDto.content.replace("\\n", it) }?.replace("\"", "")
                if (reviewDto.isMe) btnDelete.visibility = View.VISIBLE
                else btnDelete.visibility = View.GONE
                btnDelete.setOnClickListener { itemClickListener.onClickDelete(it, position, itemList[position]) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return  Holder(ItemRvReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val dto = itemList[position]
        holder.apply {
            bindInfo(itemView, position, dto)
            itemView.tag = dto
        }
    }

    override fun getItemCount(): Int = itemList.size

    interface ItemClickListener{
        fun onClickDelete(view: View, position: Int, reviewDto: PhotographerReviewDomainDto)
    }

    private lateinit var itemClickListener: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener){ this.itemClickListener = itemClickListener }
}