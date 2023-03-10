package com.ssafy.smile.presentation.view.home

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.speech.RecognizerIntent
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.smile.R
import com.ssafy.smile.common.util.hideKeyboard
import com.ssafy.smile.databinding.FragmentSearchBinding
import com.ssafy.smile.presentation.adapter.SearchViewPagerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.home.SearchViewModel

private const val TAG = "SearchFragment_스마일"
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search) {
    private val searchViewModel: SearchViewModel by navGraphViewModels(R.id.searchGraph)

    private lateinit var selected: String

    private val sttLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.data == null || it.resultCode != Activity.RESULT_OK) {
            return@registerForActivityResult
        }

        val results = it.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) //인식된 데이터 list 받아옴.

        AlertDialog.Builder(requireContext())
            .setSingleChoiceItems(results!!.toTypedArray(), -1) { _, pos ->
                selected = results[pos]
            }
            .setPositiveButton("확인") { _, _ ->
                binding.etSearch.setText(selected)
                searchCategory(selected)
            }
            .setNegativeButton("취소") { _, _ ->
                selected = ""
            }.create()
            .show()
    }

    override fun initView() {
        setViewPager()
        initToolbar()
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.layoutToolbar.tbToolbar
        toolbar.initToolbar("검색", true) { moveToPopUpSelf() }
    }

    private fun moveToPopUpSelf() = findNavController().navigate(R.id.action_searchFragment_pop)

    override fun setEvent() {
        setSearchEvent()
        binding.apply {
            btnStt.setOnClickListener {
                startStt()
            }
        }
    }

    private fun setSearchEvent() {
        binding.apply {
            etSearch.setOnKeyListener { view, keyCode, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                    hideKeyboard(view)
                    searchCategory(etSearch.text.toString())
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun searchCategory(category: String) {
        searchViewModel.searchCategory = category
        searchViewModel.searchPhotographer(category)
        searchViewModel.searchPost(category)

        Log.d(TAG, "searchCategory: ${searchViewModel.searchCategory}")
    }

    private fun setViewPager() {
        val viewPagerAdapter = SearchViewPagerAdapter(this)
        val tabTitle = listOf("작가", "게시물")

        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    private fun startStt() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, requireActivity().packageName)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
            putExtra(RecognizerIntent.EXTRA_PROMPT, "검색어를 말씀해주세요.")
        }

        sttLauncher.launch(intent)
    }
}