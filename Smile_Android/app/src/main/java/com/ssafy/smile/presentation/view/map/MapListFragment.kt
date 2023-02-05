package com.ssafy.smile.presentation.view.map

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gun0912.tedpermission.PermissionListener
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.ssafy.smile.R
import com.ssafy.smile.common.util.AddressUtils
import com.ssafy.smile.common.util.PermissionUtils
import com.ssafy.smile.databinding.FragmentAddressMapBinding
import com.ssafy.smile.databinding.FragmentMapListBinding
import com.ssafy.smile.domain.model.AddressDomainDto
import com.ssafy.smile.domain.model.PostSearchDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.adapter.AddressRVAdapter
import com.ssafy.smile.presentation.adapter.ClusterPostRVAdapter
import com.ssafy.smile.presentation.base.BaseBottomSheetDialogFragment
import com.ssafy.smile.presentation.viewmodel.home.AddressGraphViewModel
import com.ssafy.smile.presentation.viewmodel.map.MapViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapListFragment : BaseBottomSheetDialogFragment<FragmentMapListBinding>(FragmentMapListBinding::inflate) {

    private val viewModel : MapViewModel by viewModels()
    private lateinit var clusterPostRvAdapter : ClusterPostRVAdapter

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
        initRvAdapter()
    }

    override fun setEvent() { }

    private fun initRvAdapter(){
        clusterPostRvAdapter = ClusterPostRVAdapter().apply {
            setItemClickListener(object : ClusterPostRVAdapter.ItemClickListener{
                override fun onClickItem(view: View, position: Int, postSearchDto: PostSearchDomainDto) {
                    val action = MapListFragmentDirections.actionMapListFragmentToPortfolioGraph(postSearchDto.articleId)
                    findNavController().navigate(action)
                }
            })
        }
        binding.rvPost.adapter = clusterPostRvAdapter
    }

//    private fun setEmptyView(isSet : Boolean){
//        if (isSet){
//            binding.layoutAddressEmptyView.layoutEmptyView.visibility = View.VISIBLE
//            binding.layoutAddressEmptyView.tvEmptyView.text = getString(R.string.tv_address_empty)
//        }else binding.layoutAddressEmptyView.layoutEmptyView.visibility = View.GONE
//    }
//
//    private fun setRVView(isSet : Boolean, itemList : ArrayList<AddressDomainDto> = arrayListOf()){
//        if (isSet) {
//            binding.layoutRvAddress.visibility = View.VISIBLE
//            rvAdapter.setListData(itemList)
//        } else binding.layoutRvAddress.visibility = View.GONE
//    }


}