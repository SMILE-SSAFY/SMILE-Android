package com.ssafy.smile.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.smile.data.remote.model.PostListItem
import com.ssafy.smile.databinding.RecyclerPortfolioPostItemBinding

private const val TAG = "PortfolioRecyclerAdapte_스마일"
class PortfolioRecyclerAdapter(val context: Context, val datas: MutableList<PostListItem>): RecyclerView.Adapter<PortfolioRecyclerAdapter.PortfolioRecyclerViewHolder>() {

    interface OnItemClickListener {
        fun onClick(view: View, position: Int)
    }
    lateinit var onItemClickListener : OnItemClickListener
    fun setItemClickListener(onItemClickListener: OnItemClickListener) { this.onItemClickListener = onItemClickListener }

    inner class PortfolioRecyclerViewHolder(val binding: RecyclerPortfolioPostItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PostListItem) {
            binding.apply {
                Glide.with(context)
                    .load(data.photoUrl)
                    .into(binding.ivPostImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioRecyclerViewHolder {
        return PortfolioRecyclerViewHolder(RecyclerPortfolioPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PortfolioRecyclerViewHolder, position: Int) {
        holder.apply {
            bind(datas[position])

            itemView.setOnClickListener {
                onItemClickListener.onClick(it, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}