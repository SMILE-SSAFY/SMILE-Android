package com.ssafy.smile.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smile.databinding.RecyclerPostItemBinding
import com.ssafy.smile.domain.model.CustomPostDomainDto

class ResultPostRecyclerAdapter(val context: Context, val datas: MutableList<CustomPostDomainDto>): RecyclerView.Adapter<ResultPostRecyclerAdapter.ResultPostRecyclerViewHolder>() {

    interface OnPostHeartItemClickListener {
        fun onClick(view: View, position: Int)
    }
    lateinit var onPostHeartItemClickListener : OnPostHeartItemClickListener
    fun setPostHeartItemClickListener(onPostHeartItemClickListener: OnPostHeartItemClickListener) { this.onPostHeartItemClickListener = onPostHeartItemClickListener }

    interface OnItemClickListener {
        fun onClick(view: View, position: Int)
    }
    lateinit var onItemClickListener : OnItemClickListener
    fun setItemClickListener(onItemClickListener: OnItemClickListener) { this.onItemClickListener = onItemClickListener }

    inner class ResultPostRecyclerViewHolder(val binding: RecyclerPostItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CustomPostDomainDto, position: Int) {
            binding.apply {
                customPost.setAttrs(CustomPostDomainDto(
                    data.articleId,
                    data.photographerId,
                    data.img,
                    data.category,
                    data.name,
                    data.location,
                    data.isLike,
                    data.like
                ))

                val button = customPost.ctvLike
                button.setOnClickListener {
                    button.isChecked = !(button.isChecked)
                    onPostHeartItemClickListener.onClick(it, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultPostRecyclerViewHolder {
        return ResultPostRecyclerViewHolder(RecyclerPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ResultPostRecyclerViewHolder, position: Int) {
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