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
import com.ssafy.smile.presentation.viewmodel.mypage.PhotographerWriteGraphViewModel
import java.lang.ref.WeakReference


class CategoryRVAdapter(private val viewModel : PhotographerWriteGraphViewModel, private val addBtnView:Button, private val limit:Int=5) : RecyclerView.Adapter<CategoryRVAdapter.Holder>() {
    private val itemList : ArrayList<CategoryDomainDto> = arrayListOf()

    fun getListData() : ArrayList<CategoryDomainDto> = itemList

    fun setListData(dataList: ArrayList<CategoryDomainDto>){
        itemList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun addData() {
        if (itemCount<=limit){
            itemList.add(CategoryDomainDto())
            notifyItemInserted(itemCount-1)
        }
    }

    fun deleteData(index: Int){
        itemList.removeAt(index)
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
                    setText(dto.name)
                    setAdapter(Spinners.getSelectedArrayAdapter(itemView.context, R.array.spinner_category))
                    setOnItemClickListener { _, _, position, _ ->
                        dto.name = this.getString()
                        dto.categoryId = position + 1
                        dto.isEmpty = !(dto.price!=null && dto.description!=null)
                        viewModel.uploadCategoriesData(getListData())
                    }
                }
                etPhotographerCategoryPrice.apply {
                    clearPriceTextChangeListener(this)
                    val priceString = if (dto.price==null) null else dto.price!!.makeComma()
                    setText(priceString)
                    dto.priceTextWatcher = OnCurrentPriceTextWatcher(dto, this)
                    addTextChangedListener(dto.priceTextWatcher)
                }
                etPhotographerCategoryDetail.apply {
                    clearContentTextChangeListener(this)
                    setText(dto.description)
                    dto.contentTextWatcher = OnCurrentContentTextWatcher(dto)
                    addTextChangedListener(dto.contentTextWatcher)
                }
                if (position==0) btnDelete.visibility = View.INVISIBLE
                else btnDelete.visibility = View.VISIBLE
                btnDelete.setOnClickListener {
                    itemClickListener.onClickBtnDelete(it, position, dto)
                }
            }
        }

        private fun clearPriceTextChangeListener(priceEditText : EditText){
            for (position in 0 until itemCount){
                priceEditText.removeTextChangedListener(itemList[position].priceTextWatcher)
            }
        }
        private fun clearContentTextChangeListener(contentEditText: EditText){
            for (position in 0 until itemCount){
                contentEditText.removeTextChangedListener(itemList[position].contentTextWatcher)
            }
        }
    }

    inner class OnCurrentPriceTextWatcher(private val dto:CategoryDomainDto, editText: EditText?) : TextWatcher {
        private val editTextWeakReference: WeakReference<EditText>
        init { editTextWeakReference = WeakReference<EditText>(editText) }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }
        override fun afterTextChanged(editable: Editable) {
            val editText: EditText = editTextWeakReference.get() ?: return
            val s = editable.toString()
            if (s.isEmpty()) {
                dto.price = null
                return
            }
            editText.removeTextChangedListener(this)
            val price = s.replace("""[,원]""".toRegex(), "").toInt()
            val formatted: String = price.makeComma()
            editText.setText(formatted)
            editText.setSelection(formatted.length)
            editText.addTextChangedListener(this)
            dto.price = price
            dto.isEmpty = dto.name == null
            viewModel.uploadCategoriesData(getListData())
        }
    }

    inner class OnCurrentContentTextWatcher(private val dto:CategoryDomainDto) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            dto.description = s.toString()
            dto.isEmpty = !(dto.name!=null && dto.price!=null)
            viewModel.uploadCategoriesData(getListData())
        }
        override fun afterTextChanged(s: Editable) {}
    }


}