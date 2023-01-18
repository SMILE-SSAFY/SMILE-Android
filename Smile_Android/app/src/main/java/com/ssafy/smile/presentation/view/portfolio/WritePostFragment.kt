package com.ssafy.smile.presentation.view.portfolio


import android.widget.ArrayAdapter
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentWritePostBinding
import com.ssafy.smile.presentation.base.BaseFragment

class WritePostFragment : BaseFragment<FragmentWritePostBinding>(FragmentWritePostBinding::bind, R.layout.fragment_write_post) {
    private val spinnerAdapter : ArrayAdapter<String> by lazy {
        val items = resources.getStringArray(R.array.spinner_category)
        ArrayAdapter(requireContext(), R.layout.item_spinner_category, items)
    }

    override fun initView() {
        mainActivity.setToolBar(isUsed = true, isBackUsed = true, title = "게시글 업로드")
        setSpinnerAdapter()
    }

    override fun setEvent() {

    }

    private fun setSpinnerAdapter(){
       binding.apply {
           tvCategoryContent.setAdapter(spinnerAdapter)
           tvCategoryContent.setOnItemClickListener { _, _, _, _ ->
               showToast(requireContext(), tvCategoryContent.text.toString())
           }
       }
    }
}