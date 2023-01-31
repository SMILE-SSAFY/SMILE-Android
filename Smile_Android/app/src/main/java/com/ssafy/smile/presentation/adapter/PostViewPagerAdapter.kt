package com.ssafy.smile.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.smile.common.util.Constants
import com.ssafy.smile.databinding.ViewpagerPostImageItemBinding

class PostViewPagerAdapter(val context: Context, val datas: MutableList<String>): RecyclerView.Adapter<PostViewPagerAdapter.PostViewPagerViewHolder>() {
    inner class PostViewPagerViewHolder(val binding: ViewpagerPostImageItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.apply {
                Glide.with(context)
                    .load(Constants.IMAGE_BASE_URL+data)
                    .into(binding.ivImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewPagerViewHolder {
        return PostViewPagerViewHolder(ViewpagerPostImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PostViewPagerViewHolder, position: Int) {
        holder.apply {
            bind(datas[position])
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}