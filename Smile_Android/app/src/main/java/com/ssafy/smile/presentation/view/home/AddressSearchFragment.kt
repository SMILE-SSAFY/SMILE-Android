package com.ssafy.smile.presentation.view.home

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
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
    private val viewModel : AddressGraphViewModel by navGraphViewModels(R.id.addressGraph)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            setupRatio(bottomSheet, 90)
        }
        return dialog
    }

    override fun initView() {
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
        viewModel.insertAddressResponseLiveData.observe(viewLifecycleOwner){
            if (it<0) showToast(requireContext(), "주소 선택 과정 중 에러가 발생했습니다. 잠시 후, 다시 시도해주세요.", Types.ToastType.ERROR)
            else this@AddressSearchFragment.dismiss()
        }
    }

    private inner class BridgeInterface(){
        @JavascriptInterface
        fun processDATA(data : String){
            val latLng = AddressUtils.getPointsFromGeo(this@AddressSearchFragment.requireContext(), data)
            val addressDto = AddressDomainDto(address = data).apply {
                latLng?.let {
                    latitude = it.latitude
                    longitude = it.longitude
                }
            }
            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    viewModel.insertAddress(addressDto)
                }
            }
        }
    }


}