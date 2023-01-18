package com.ssafy.smile.presentation.view.portfolio

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import android.widget.ArrayAdapter
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.smile.R
import com.ssafy.smile.common.util.PermissionUtils.actionGalleryPermission
import com.ssafy.smile.databinding.FragmentWritePostBinding
import com.ssafy.smile.presentation.adapter.ImageRvAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import java.io.FileNotFoundException
import kotlin.math.abs

class WritePostFragment : BaseFragment<FragmentWritePostBinding>(FragmentWritePostBinding::bind, R.layout.fragment_write_post) {

    private lateinit var imageRvAdapter: ImageRvAdapter
    private val spinnerAdapter: ArrayAdapter<String> by lazy {
        val items = resources.getStringArray(R.array.spinner_category)
        ArrayAdapter(requireContext(), R.layout.item_spinner_category, items)
    }

    override fun initView() {
        mainActivity.setToolBar(isUsed = true, isBackUsed = true, title = "게시글 업로드")
        setRvAdapter()
        setSpinnerAdapter()
    }

    override fun setEvent() {
        binding.apply {
            btnPictureContent.setOnClickListener {
                actionGalleryPermission(requireContext(), abs(3-imageRvAdapter.itemCount)){
                    imageRvAdapter.setListData(ArrayList(it))
                }
            }
        }
    }

    private fun setRvAdapter(){
        imageRvAdapter = ImageRvAdapter(requireContext()).apply {
            setItemClickListener(object : ImageRvAdapter.ItemClickListener{
                override fun onClickBtnDelete(view: View, position: Int, dto: Uri) {
                    deleteItem(position)
                }
            })
        }
        binding.rvPictureContent.apply {
            adapter = imageRvAdapter
        }
    }

    private fun setSpinnerAdapter() {
        binding.apply {
            tvCategoryContent.setAdapter(spinnerAdapter)
            tvCategoryContent.setOnItemClickListener { _, _, _, _ ->
                showToast(requireContext(), tvCategoryContent.text.toString())
            }
        }
    }

}