package com.ssafy.smile.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smile.R
import com.ssafy.smile.common.util.getString
import com.ssafy.smile.common.util.setOnCurrentTextWatchListener
import com.ssafy.smile.databinding.ItemPhotographerCategoryBinding
import com.ssafy.smile.domain.model.CategoryDto
import com.ssafy.smile.domain.model.Spinners


class CategoryRVAdapter(private val addBtnView:Button,private val limit:Int=5) : RecyclerView.Adapter<CategoryRVAdapter.Holder>() {
    private val itemList : ArrayList<CategoryDto> = arrayListOf()

    fun getListData() : ArrayList<CategoryDto> = itemList

    fun setListData(dataList: ArrayList<CategoryDto>){
        itemList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun addData() {
        if (itemCount<=limit){
            itemList.add(CategoryDto())
            notifyDataSetChanged()
        }
    }

    fun deleteItem(index: Int){
        itemList.removeAt(index)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (itemList.isEmpty()) itemList.add(CategoryDto())
        else if (itemList.size==limit) addBtnView.visibility = View.GONE
        else addBtnView.visibility = View.VISIBLE
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return  Holder(ItemPhotographerCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val dto = itemList[position]
        holder.apply {
            bindInfo(position, dto)
            itemView.tag = dto
        }
    }

    interface ItemClickListener{
        fun onClickBtnDelete(view: View, position: Int, dto:CategoryDto)
    }
    private lateinit var itemClickListener: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener){ this.itemClickListener = itemClickListener }


    inner class Holder(private val binding: ItemPhotographerCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(position: Int, dto: CategoryDto) {
            binding.apply {
                tvPhotographerCategory.apply {
                    setAdapter(Spinners.getSelectedArrayAdapter(itemView.context, R.array.spinner_category))
                    setOnItemClickListener { _, _, _, _ ->
                        dto.categoryName = this.getString()
                    }
                    setText(dto.categoryName)
                }
                etPhotographerCategoryPrice.setOnCurrentTextWatchListener()
                btnDelete.setOnClickListener { itemClickListener.onClickBtnDelete(it, position, dto) }
            }
        }
    }

}