package com.ssafy.smile.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smile.databinding.RecyclerPhotographerItemBinding
import com.ssafy.smile.domain.model.CustomPhotographerDomainDto

class MyInterestPhotographerRVAdapter() : RecyclerView.Adapter<MyInterestPhotographerRVAdapter.Holder>() {

    private val itemList : ArrayList<CustomPhotographerDomainDto> = arrayListOf()

    fun setListData(dataList: ArrayList<CustomPhotographerDomainDto>){
        itemList.clear()
        itemList.addAll(dataList)
        notifyDataSetChanged()
    }


    inner class Holder(private val binding: RecyclerPhotographerItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindInfo(itemView : View, position: Int, photographerDto: CustomPhotographerDomainDto){
            binding.apply {
                customPhotographer.setAttrs(
                    CustomPhotographerDomainDto(
                        photographerDto.photographerId,
                        photographerDto.img,
                        photographerDto.category,
                        photographerDto.name,
                        photographerDto.location,
                        photographerDto.price,
                        photographerDto.isLike,
                        photographerDto.like
                    )
                )
                customPhotographer.ctvLike.setOnClickListener {
                    itemClickListener.onClickHeart(it as CheckedTextView, position, itemList[position])
                }
            }
            itemView.setOnClickListener { itemClickListener.onClickItem(it, position, itemList[position])  }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return  Holder(RecyclerPhotographerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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
        fun onClickItem(view: View, position: Int, photographerDto: CustomPhotographerDomainDto)
        fun onClickHeart(view: CheckedTextView, position: Int, photographerDto: CustomPhotographerDomainDto)
    }

    private lateinit var itemClickListener: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener){ this.itemClickListener = itemClickListener }
}