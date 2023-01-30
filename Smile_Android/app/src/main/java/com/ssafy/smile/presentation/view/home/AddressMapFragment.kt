package com.ssafy.smile.presentation.view.home

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.navigation.navGraphViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.smile.R
import com.ssafy.smile.databinding.FragmentAddressMapBinding
import com.ssafy.smile.presentation.adapter.AddressRVAdapter
import com.ssafy.smile.presentation.base.BaseBottomSheetDialogFragment
import com.ssafy.smile.presentation.viewmodel.home.AddressGraphViewModel

class AddressMapFragment : BaseBottomSheetDialogFragment<FragmentAddressMapBinding>(FragmentAddressMapBinding::inflate) {

    private val viewModel : AddressGraphViewModel by navGraphViewModels(R.id.addressGraph)

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private const val UPDATE_INTERVAL = 1000
        private const val FASTEST_UPDATE_INTERVAL = 500
    }

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

    }

    override fun setEvent() {

    }

}