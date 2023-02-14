package com.ssafy.smile.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smile.databinding.RecyclerPhotographerItemBinding
import com.ssafy.smile.databinding.RecyclerPostItemBinding
import com.ssafy.smile.domain.model.CustomPhotographerDomainDto
import com.ssafy.smile.domain.model.CustomPostDomainDto

class MyInterestArticleRVAdapter() : RecyclerView.Adapter<MyInterestArticleRVAdapter.Holder>() {

    private val itemList : ArrayList<CustomPostDomainDto> = arrayListOf()

    fun setListData(dataList: ArrayList<CustomPostDomainDto>){
        itemList.clear()
        itemList.addAll(dataList)
        notifyDataSetChanged()
    }

    inner class Holder(private val binding: RecyclerPostItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindInfo(itemView : View, position: Int, articleDto: CustomPostDomainDto){
            binding.apply {
                customPost.setAttrs(
                    CustomPostDomainDto(
                        articleDto.articleId,
                        articleDto.photographerId,
                        articleDto.img,
                        articleDto.category,
                        articleDto.name,
                        articleDto.location,
                        articleDto.isLike,
                        articleDto.like
                    )
                )
                customPost.ctvLike.setOnClickListener {
                    itemClickListener.onClickHeart(it as CheckedTextView, position, itemList[position])
                }
            }
            itemView.setOnClickListener { itemClickListener.onClickItem(it, position, itemList[position])  }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return  Holder(RecyclerPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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
        fun onClickItem(view: View, position: Int, articleDto: CustomPostDomainDto)
        fun onClickHeart(view: CheckedTextView, position: Int, articleDto: CustomPostDomainDto)
    }

    private lateinit var itemClickListener: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener){ this.itemClickListener = itemClickListener }
}