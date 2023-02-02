package com.ssafy.smile.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smile.databinding.RecyclerCustomerReservationItemBinding
import com.ssafy.smile.domain.model.CustomReservationDomainDto

class CustomerReservationListRecyclerAdapter(val context: Context, val datas: MutableList<CustomReservationDomainDto>): RecyclerView.Adapter<CustomerReservationListRecyclerAdapter.CustomerReservationListViewHolder>() {

    interface OnCancelClickListener {
        fun onClick(view: View, position: Int)
    }
    lateinit var onCancelClickListener : OnCancelClickListener
    fun setCancelClickListener(onCancelClickListener: OnCancelClickListener) { this.onCancelClickListener = onCancelClickListener }

    interface OnPayFixClickListener {
        fun onClick(view: View, position: Int)
    }
    lateinit var onPayFixClickListener : OnPayFixClickListener
    fun setPayFixClickListener(onPayFixClickListener: OnPayFixClickListener) { this.onPayFixClickListener = onPayFixClickListener }

    interface OnReviewClickListener {
        fun onClick(view: View, position: Int)
    }
    lateinit var onReviewClickListener : OnReviewClickListener
    fun setReviewClickListener(onReviewClickListener: OnReviewClickListener) { this.onReviewClickListener = onReviewClickListener }

    inner class CustomerReservationListViewHolder(val binding: RecyclerCustomerReservationItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CustomReservationDomainDto, position: Int) {
            binding.apply {
                data.opposite = "작가님"
                customReservation.setAttrs(data, true)

                val cancelBtn = customReservation.btnCancel
                cancelBtn.setOnClickListener {
                    onCancelClickListener.onClick(it, position)
                }

                val payFixBtn = customReservation.btnPayFix
                payFixBtn.setOnClickListener {
                    onPayFixClickListener.onClick(it, position)
                }

                val reviewBtn = customReservation.btnReview
                reviewBtn.setOnClickListener {
                    onReviewClickListener.onClick(it, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerReservationListViewHolder {
        return CustomerReservationListViewHolder(RecyclerCustomerReservationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CustomerReservationListViewHolder, position: Int) {
        holder.apply {
            bind(datas[position], position)
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}