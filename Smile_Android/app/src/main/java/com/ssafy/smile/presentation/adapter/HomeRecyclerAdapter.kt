package com.ssafy.smile.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smile.databinding.RecyclerHomeItemBinding
import com.ssafy.smile.domain.model.CustomPhotographerDomainDto

class HomeRecyclerAdapter(val context: Context, val datas: MutableList<CustomPhotographerDomainDto>): RecyclerView.Adapter<HomeRecyclerAdapter.HomeRecyclerViewHolder>() {

    interface OnPhotographerHeartItemClickListener {
        fun onClick(view: View, position: Int)
    }
    lateinit var onPhotographerHeartItemClickListener : OnPhotographerHeartItemClickListener
    fun setPhotographerHeartItemClickListener(onPhotographerHeartItemClickListener: OnPhotographerHeartItemClickListener) { this.onPhotographerHeartItemClickListener = onPhotographerHeartItemClickListener }

    interface OnItemClickListener {
        fun onClick(view: View, position: Int)
    }
    lateinit var onItemClickListener : OnItemClickListener
    fun setItemClickListener(onItemClickListener: OnItemClickListener) { this.onItemClickListener = onItemClickListener }

    inner class HomeRecyclerViewHolder(val binding: RecyclerHomeItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CustomPhotographerDomainDto, position: Int) {
            binding.apply {
                customPhotographer.setAttrs(
                    CustomPhotographerDomainDto(
                        data.photographerId,
                        data.img,
                        data.category,
                        data.name,
                        data.location,
                        data.price,
                        data.isLike,
                        data.like
                    )
                )

                val button = customPhotographer.ctvLike
                button.setOnClickListener {
                    button.isChecked = !(button.isChecked)
                    onPhotographerHeartItemClickListener.onClick(it, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        return HomeRecyclerViewHolder(
            RecyclerHomeItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        holder.apply {
            bind(datas[position], position)

            itemView.setOnClickListener {
                onItemClickListener.onClick(it, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}