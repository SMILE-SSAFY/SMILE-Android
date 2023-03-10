package com.ssafy.smile.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.smile.databinding.ItemRvImageBinding
import java.io.File

class ImageRVAdapter(private val context: Context) : RecyclerView.Adapter<ImageRVAdapter.Holder>() {
    private val itemList : ArrayList<File> = arrayListOf()
    fun getListData() = itemList

    fun setListData(dataList: ArrayList<File>){
        itemList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun deleteItem(index: Int){
        itemList.removeAt(index)
        notifyDataSetChanged()
    }

    inner class Holder(private val binding: ItemRvImageBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindInfo(position: Int, dto: File){
            Glide.with(context).load(dto).into(binding.ivImageImg)
            binding.btnImageClose.setOnClickListener { itemClickListener.onClickBtnDelete(it, position, itemList[position]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return  Holder(ItemRvImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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
        fun onClickBtnDelete(view: View, position: Int, dto:File)
    }

    private lateinit var itemClickListener: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener){ this.itemClickListener = itemClickListener }
}