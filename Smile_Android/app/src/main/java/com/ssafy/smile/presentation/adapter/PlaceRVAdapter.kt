package com.ssafy.smile.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smile.R
import com.ssafy.smile.common.util.getString
import com.ssafy.smile.databinding.ItemPhotographerPlaceBinding
import com.ssafy.smile.domain.model.PlaceDomainDto
import com.ssafy.smile.domain.model.Spinners
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.viewmodel.mypage.PhotographerWriteGraphViewModel


class PlaceRVAdapter(private val viewModel : PhotographerWriteGraphViewModel, private val addBtnView:Button, private val limit:Int=5) : RecyclerView.Adapter<PlaceRVAdapter.Holder>() {
    private val itemList : ArrayList<PlaceDomainDto> = arrayListOf()

    fun getListData() : ArrayList<PlaceDomainDto> = itemList

    fun setListData(dataList: ArrayList<PlaceDomainDto>){
        itemList.clear()
        itemList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun addData() {
        if (itemCount<=limit){
            itemList.add(PlaceDomainDto())
            notifyDataSetChanged()
            viewModel.uploadPlacesData(getListData())
        }
    }

    fun deleteItem(index: Int){
        itemList.removeAt(index)
        notifyDataSetChanged()
        viewModel.uploadPlacesData(getListData())
    }

    override fun getItemCount(): Int {
        if (itemList.isEmpty()) itemList.add(PlaceDomainDto())
        else if (itemList.size==limit) addBtnView.visibility = View.GONE
        else addBtnView.visibility = View.VISIBLE
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return  Holder(ItemPhotographerPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val dto = itemList[position]
        holder.apply {
            bindInfo(position, dto)
            itemView.tag = dto
        }
    }

    interface ItemClickListener{
        fun onClickBtnDelete(view: View, position: Int, dto:PlaceDomainDto)
    }
    private lateinit var itemClickListener: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener){ this.itemClickListener = itemClickListener }


    inner class Holder(private val binding: ItemPhotographerPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(position: Int, dto: PlaceDomainDto) {
            binding.apply {
                tvPhotographerPlaceFirst.run {
                    setOnItemClickListener { _, _, position, _ ->
                        val resource = Spinners.getSelectedPlaceArrayResource(this.getString())
                        tvPhotographerPlaceSecond.setAdapter(Spinners.getSelectedArrayAdapter(itemView.context, resource))
                        tvPhotographerPlaceSecond.isEnabled = true
                        tvPhotographerPlaceSecond.text = null
                        dto.isEmpty = true
                        dto.first = this.getString()
                        dto.firstId = position
                    }
                    setText(dto.first)
                    setAdapter(Spinners.getSelectedArrayAdapter(itemView.context, R.array.spinner_region))
                }
                tvPhotographerPlaceSecond.apply {
                    isEnabled = false
                    setOnItemClickListener { _, _, position, _ ->
                        dto.isEmpty = false
                        dto.second = this.getString()
                        dto.secondId = position
                        viewModel.uploadPlacesData(getListData())
                    }
                    setText(dto.second)
                    dto.first?.let {
                        isEnabled = true
                        val resource = Spinners.getSelectedPlaceArrayResource(it)
                        setAdapter(Spinners.getSelectedArrayAdapter(itemView.context, resource))
                    }
                }
                if (position==0) btnDelete.visibility = View.INVISIBLE
                else btnDelete.visibility = View.VISIBLE
                btnDelete.setOnClickListener { itemClickListener.onClickBtnDelete(it, position, dto) }
            }
        }
    }

}
