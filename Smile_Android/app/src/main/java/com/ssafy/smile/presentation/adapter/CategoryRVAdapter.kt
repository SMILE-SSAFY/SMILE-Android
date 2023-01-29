package com.ssafy.smile.presentation.adapter

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smile.R
import com.ssafy.smile.common.util.getString
import com.ssafy.smile.common.util.makeComma
import com.ssafy.smile.databinding.ItemPhotographerCategoryBinding
import com.ssafy.smile.domain.model.CategoryDomainDto
import com.ssafy.smile.domain.model.Spinners
import java.lang.ref.WeakReference

private const val TAG = "CategoryRVAdapter"
class CategoryRVAdapter(private val addBtnView:Button,private val limit:Int=5) : RecyclerView.Adapter<CategoryRVAdapter.Holder>() {
    private val itemList : ArrayList<CategoryDomainDto> = arrayListOf()

    fun getListData() : ArrayList<CategoryDomainDto> = itemList

    fun setListData(dataList: ArrayList<CategoryDomainDto>){
        itemList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun addData() {
        if (itemCount<=limit){
            itemList.add(CategoryDomainDto())
            Log.d(TAG, "addData: $itemList")
            notifyDataSetChanged()
        }
    }

    fun deleteData(index: Int){
        itemList.removeAt(index)
        Log.d(TAG, "deleteItem: $itemList")
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (itemList.isEmpty()) itemList.add(CategoryDomainDto())
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
        fun onClickBtnDelete(view: View, position: Int, dto:CategoryDomainDto)
    }
    private lateinit var itemClickListener: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener){ this.itemClickListener = itemClickListener }


    inner class Holder(private val binding: ItemPhotographerCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(position: Int, dto: CategoryDomainDto) {
            binding.apply {
                tvPhotographerCategory.apply {
                    setAdapter(Spinners.getSelectedArrayAdapter(itemView.context, R.array.spinner_category))
                    setOnItemClickListener { _, _, position, _ ->
                        dto.name = this.getString()
                        dto.categoryId = position
                    }
                    setText(dto.name)
                }
                etPhotographerCategoryPrice.apply {
                    doOnTextChanged { charSequence, _, _, _ ->
                        itemList[position].price = if (charSequence.toString()=="") 0 else charSequence.toString().toInt()
                    }
                    val priceString = if (dto.price==0) null else dto.price.toString()
                    setText(priceString)
                //addTextChangedListener(OnCurrentTextWatcher(position, this))
                }
                etPhotographerCategoryDetail.apply {
                    doOnTextChanged { charSequence, _, _, _ ->
                        itemList[position].description = charSequence.toString()
                    }
                    setText(dto.description)
                }
                if (position==0) btnDelete.visibility = View.INVISIBLE
                else btnDelete.visibility = View.VISIBLE
                btnDelete.setOnClickListener {
                    itemClickListener.onClickBtnDelete(it, position, dto)
                }
            }
        }
    }

    inner class OnCurrentTextWatcher(private val position: Int, editText: EditText?) : TextWatcher {
        private val editTextWeakReference: WeakReference<EditText>
        init { editTextWeakReference = WeakReference<EditText>(editText) }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(editable: Editable) {
            val editText: EditText = editTextWeakReference.get() ?: return
            val s = editable.toString()
            if (s.isEmpty()) return
            editText.removeTextChangedListener(this)
            val price = s.replace("""[,원]""".toRegex(), "").toInt()
            val formatted: String = price.makeComma()
            editText.setText(formatted)
            editText.setSelection(formatted.length)
            editText.addTextChangedListener(this)
            itemList[position].price = price
        }
    }


}