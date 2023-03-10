package com.ssafy.smile.presentation.view.home

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.smile.R
import com.ssafy.smile.common.util.AddressUtils
import com.ssafy.smile.databinding.FragmentAddressSearchBinding
import com.ssafy.smile.domain.model.AddressDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseBottomSheetDialogFragment
import com.ssafy.smile.presentation.viewmodel.home.AddressGraphViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddressSearchFragment : BaseBottomSheetDialogFragment<FragmentAddressSearchBinding>(FragmentAddressSearchBinding::inflate) {

    private val navArgs : AddressSearchFragmentArgs by navArgs()
    private val viewModel : AddressGraphViewModel by viewModels()
    private var isSelectionMode : Boolean = false
    private var addressDomainDto : AddressDomainDto = AddressDomainDto()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet!!).apply {
                state = BottomSheetBehavior.STATE_EXPANDED
                isDraggable = false
            }
            setupRatio(bottomSheet, 90)
        }
        return dialog
    }

    override fun initView() {
        isSelectionMode = navArgs.isSelectionMode
        binding.webView.apply {
            settings.javaScriptEnabled = true
            addJavascriptInterface(BridgeInterface(), "Android")
            webViewClient = object : WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.webView.loadUrl(getString(R.string.address_search_execute_url))
                }
            }
            loadUrl(getString(R.string.address_search_url))
        }
    }

    override fun setEvent() {
        binding.btnBack.setOnClickListener { moveToPopUpSelf() }
        viewModel.selectAddressResponseLiveData.observe(viewLifecycleOwner){
            if (it<0) showToast(requireContext(), requireContext().getString(R.string.msg_common_error, "????????????"), Types.ToastType.ERROR)
            else {
                findNavController().getBackStackEntry(R.id.mainFragment).savedStateHandle["curAddress"] = addressDomainDto
                showToast(requireContext(), getString(R.string.msg_address_success), Types.ToastType.BASIC)
                moveToPopUpToGraph()
            }
        }
        viewModel.insertAddressResponseLiveData.observe(viewLifecycleOwner){
            if (it<0) showToast(requireContext(),  requireContext().getString(R.string.msg_common_error, "????????????"), Types.ToastType.ERROR)
            else{
                val bundle = Bundle().apply { putParcelable("addressDomainDto", addressDomainDto) }
                requireActivity().supportFragmentManager.setFragmentResult("getAddress",bundle)
                moveToPopUpToGraph()
            }
        }
    }

    private inner class BridgeInterface(){
        @JavascriptInterface
        fun processDATA(data : String){
            val latLng = AddressUtils.getPointsFromGeo(this@AddressSearchFragment.requireContext(), data)
            addressDomainDto = AddressDomainDto(address = data).apply {
                latLng?.let {
                    latitude = it.latitude
                    longitude = it.longitude
                }
            }
            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    if (isSelectionMode) viewModel.selectAddress(addressDomainDto.apply { isSelected = true })
                    else viewModel.insertAddress(addressDomainDto)
                }
            }
        }
    }

    private fun moveToPopUpSelf() = findNavController().navigate(R.id.action_addressSearchFragment_pop)
    private fun moveToPopUpToGraph() { findNavController().navigate(R.id.action_addressSearchFragment_pop_including_addressGraph) }

}