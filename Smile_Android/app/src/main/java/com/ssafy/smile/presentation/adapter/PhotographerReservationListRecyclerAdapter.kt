package com.ssafy.smile.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smile.databinding.RecyclerPhotographerReservationItemBinding
import com.ssafy.smile.domain.model.CustomReservationDomainDto

class PhotographerReservationListRecyclerAdapter(val context: Context, val datas: MutableList<CustomReservationDomainDto>): RecyclerView.Adapter<PhotographerReservationListRecyclerAdapter.PhotographerReservationListViewHolder>() {
    inner class PhotographerReservationListViewHolder(val binding: RecyclerPhotographerReservationItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CustomReservationDomainDto) {
            binding.apply {
                customReservation.setAttrs(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotographerReservationListViewHolder {
        return PhotographerReservationListViewHolder(RecyclerPhotographerReservationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PhotographerReservationListViewHolder, position: Int) {
        holder.apply {
            bind(datas[position])
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}