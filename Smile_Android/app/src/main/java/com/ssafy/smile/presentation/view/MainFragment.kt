package com.ssafy.smile.presentation.view


import android.view.MenuItem
import androidx.activity.addCallback
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentMainBinding
import com.ssafy.smile.presentation.adapter.MainViewPagerAdapter
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.viewmodel.MainViewModel


class MainFragment: BaseFragment<FragmentMainBinding>(FragmentMainBinding::bind, R.layout.fragment_main) {

    private val viewModel : MainViewModel by viewModels()
    private lateinit var mainViewPagerAdapter : MainViewPagerAdapter

    override fun initView() {
        binding.apply {
            initViewPager()
        }
    }

    override fun setEvent() {
        viewModel.onBackPressed.observe(viewLifecycleOwner){
            if (it==true) requireActivity().finish()
            else showToast(requireContext(), it.toString())
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.onBackPressed()
        }

    }


    private fun initViewPager(){
        binding.apply{
            mainViewPagerAdapter = MainViewPagerAdapter(requireActivity())

            vpMain.apply {
                adapter = mainViewPagerAdapter
                isUserInputEnabled = false
                offscreenPageLimit = mainViewPagerAdapter.itemCount
                registerOnPageChangeCallback(PageChangeCallback())
            }

            bnvMain.setOnItemSelectedListener {
                navigationSelected(it)
            }

            fabMain.setOnClickListener {
                vpMain.currentItem = 1
            }
        }
    }

    private inner class PageChangeCallback: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.apply {
                bnvMain.selectedItemId = when (position) {
                    0 -> R.id.menu_map
                    1 -> R.id.menu_home
                    2 -> R.id.menu_mypage
                    else -> error("no such position: $position")
                }
            }
        }
    }

    private fun navigationSelected(item: MenuItem): Boolean {
        val checked = item.setChecked(true)

        binding.apply {
            when (checked.itemId) {
                R.id.menu_map -> {
                    vpMain.currentItem = 0
                    return true
                }
                R.id.menu_mypage -> {
                    vpMain.currentItem = 2
                    return true
                }
            }
            return false
        }
    }

}