package com.ssafy.smile.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smile.databinding.RecyclerPhotographerReservationItemBinding
import com.ssafy.smile.domain.model.CustomReservationDomainDto

class PhotographerReservationListRecyclerAdapter(val context: Context, val datas: MutableList<CustomReservationDomainDto>): RecyclerView.Adapter<PhotographerReservationListRecyclerAdapter.PhotographerReservationListViewHolder>() {

    interface OnCancelClickListener {
        fun onClick(view: View, position: Int)
    }
    lateinit var onCancelClickListener : OnCancelClickListener
    fun setCancelClickListener(onCancelClickListener: OnCancelClickListener) { this.onCancelClickListener = onCancelClickListener }

    interface OnResFixClickListener {
        fun onClick(view: View, position: Int)
    }
    lateinit var onResFixClickListener : OnResFixClickListener
    fun setResFixClickListener(onResFixClickListener: OnResFixClickListener) { this.onResFixClickListener = onResFixClickListener }

    inner class PhotographerReservationListViewHolder(val binding: RecyclerPhotographerReservationItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CustomReservationDomainDto, position: Int) {
            binding.apply {
                data.opposite = "고객님"
                customReservation.setAttrs(data)

                val cancelBtn = customReservation.btnCancel
                cancelBtn.setOnClickListener {
                    onCancelClickListener.onClick(it, position)
                }

                val resFixBtn = customReservation.btnResFix
                resFixBtn.setOnClickListener {
                    onResFixClickListener.onClick(it, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotographerReservationListViewHolder {
        return PhotographerReservationListViewHolder(RecyclerPhotographerReservationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PhotographerReservationListViewHolder, position: Int) {
        holder.apply {
            bind(datas[position], position)
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}