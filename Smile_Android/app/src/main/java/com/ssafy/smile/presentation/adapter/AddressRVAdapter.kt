package com.ssafy.smile.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smile.databinding.ItemRvAddressBinding
import com.ssafy.smile.domain.model.AddressDomainDto

class AddressRVAdapter() : RecyclerView.Adapter<AddressRVAdapter.Holder>() {

    private val itemList : ArrayList<AddressDomainDto> = arrayListOf()

    fun setListData(dataList: ArrayList<AddressDomainDto>){
        itemList.clear()
        itemList.addAll(dataList)
        notifyDataSetChanged()
    }


    inner class Holder(private val binding: ItemRvAddressBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindInfo(position: Int, address: AddressDomainDto){
            binding.apply {
                if (address.isSelected) setSelectedView()
                else setUnSelectedView()
                tvAddressItem.text = address.address
                tvAddressItem.setOnClickListener { itemClickListener.onClickItem(it, position, itemList[position]) }
                btnAddressItemDelete.setOnClickListener { itemClickListener.onClickRemove(it, position, itemList[position]) }
            }
        }
        private fun setSelectedView(){
            binding.btnAddressItemDelete.visibility = View.GONE
            binding.btnAddressItemSelected.visibility = View.VISIBLE
            binding.tvAddressComment.visibility = View.VISIBLE
        }
        private fun setUnSelectedView(){
            binding.btnAddressItemDelete.visibility = View.VISIBLE
            binding.btnAddressItemSelected.visibility = View.GONE
            binding.tvAddressComment.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return  Holder(ItemRvAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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
        fun onClickItem(view: View, position: Int, addressDomainDto: AddressDomainDto)
        fun onClickRemove(view: View, position: Int, addressDomainDto: AddressDomainDto)
    }

    private lateinit var itemClickListener: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener){ this.itemClickListener = itemClickListener }
}