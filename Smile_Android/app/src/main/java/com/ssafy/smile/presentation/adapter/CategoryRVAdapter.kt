package com.ssafy.smile.presentation.adapter

import android.text.Editable
import android.text.TextWatcher
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
import com.ssafy.smile.domain.model.CategoryDto
import com.ssafy.smile.domain.model.Spinners
import java.lang.ref.WeakReference


class CategoryRVAdapter(private val addBtnView:Button,private val limit:Int=5) : RecyclerView.Adapter<CategoryRVAdapter.Holder>() {
    private val itemList : ArrayList<CategoryDto> = arrayListOf()

    fun getListData() : ArrayList<CategoryDto> = itemList

    fun setListData(dataList: ArrayList<CategoryDto>){
        itemList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun addData() {
        if (itemCount<=limit){
            itemList.add(CategoryDto())
            notifyDataSetChanged()
        }
    }

    fun deleteItem(index: Int){
        itemList.removeAt(index)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (itemList.isEmpty()) itemList.add(CategoryDto())
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
        fun onClickBtnDelete(view: View, position: Int, dto:CategoryDto)
    }
    private lateinit var itemClickListener: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener){ this.itemClickListener = itemClickListener }


    inner class Holder(private val binding: ItemPhotographerCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(position: Int, dto: CategoryDto) {
            binding.apply {
                tvPhotographerCategory.apply {
                    setAdapter(Spinners.getSelectedArrayAdapter(itemView.context, R.array.spinner_category))
                    setOnItemClickListener { _, _, _, _ ->
                        dto.categoryName = this.getString()
                    }
                    setText(dto.categoryName)
                }
                etPhotographerCategoryPrice.apply {
                    setText(dto.categoryPrice)
//                    doOnTextChanged { charSequence, _, _, _ ->
//                        if (charSequence!=null){
//                            val price = charSequence.replace("""[,원]""".toRegex(), "").toInt()
//                            val formatted: String = price.makeComma()
//                            itemList[position].categoryPrice = price.toString()
//                            setText(formatted)
//                        }
//                    }
                     //addTextChangedListener(OnCurrentTextWatcher(position, this))
                }
                etPhotographerCategoryDetail.apply {
                    setText(dto.description)
//                    doOnTextChanged { charSequence, _, _, _ ->
//                        itemList[position].description = charSequence.toString()
//                    }
                }
                if (position==0) btnDelete.visibility = View.INVISIBLE
                else btnDelete.visibility = View.VISIBLE
                btnDelete.setOnClickListener { itemClickListener.onClickBtnDelete(it, position, dto) }
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
            itemList[position].categoryPrice = price.toString()
        }
    }


}