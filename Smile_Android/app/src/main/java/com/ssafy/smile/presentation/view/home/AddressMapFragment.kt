package com.ssafy.smile.presentation.view.home

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
import com.ssafy.smile.domain.model.AddressDomainDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseBottomSheetDialogFragment
import com.ssafy.smile.presentation.viewmodel.home.AddressGraphViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddressMapFragment : BaseBottomSheetDialogFragment<FragmentAddressMapBinding>(FragmentAddressMapBinding::inflate), OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private val navArgs : AddressMapFragmentArgs by navArgs()
    private val viewModel : AddressGraphViewModel by viewModels()
    private var isSelectionMode : Boolean = false

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private const val UPDATE_INTERVAL = 1000
        private const val FASTEST_UPDATE_INTERVAL = 500
    }

    private var map: NaverMap? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var currentPosition: LatLng = LatLng(37.56, 126.97)
    private var locationSource : FusedLocationSource? = null
    private var currentMarker: Marker? = null

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
        (childFragmentManager.findFragmentById(R.id.innerMapView) as MapFragment).getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun setEvent() {
        binding.btnBack.setOnClickListener { moveToPopUpSelf() }
        viewModel.selectAddressResponseLiveData.observe(viewLifecycleOwner){
            if (it<0) showToast(requireContext(),  requireContext().getString(R.string.msg_common_error, "주소 설정"), Types.ToastType.ERROR)
            else {
                showToast(requireContext(), getString(R.string.msg_address_success), Types.ToastType.SUCCESS)
                moveToPopUpToGraph()
            }
        }
        viewModel.insertAddressResponseLiveData.observe(viewLifecycleOwner){
            if (it<0) showToast(requireContext(), requireContext().getString(R.string.msg_common_error, "주소 설정"), Types.ToastType.ERROR)
            else{
                val bundle = Bundle().apply { putParcelable("addressDomainDto", addressDomainDto) }
                requireActivity().supportFragmentManager.setFragmentResult("getAddress",bundle)
                moveToPopUpToGraph()
            }
        }
    }

    override fun onMapReady(nMap: NaverMap) {
        with(nMap) {
            uiSettings.isLocationButtonEnabled = false
            uiSettings.isZoomGesturesEnabled = true
            uiSettings.isZoomControlEnabled = false
            locationTrackingMode = LocationTrackingMode.Follow
        }
        this.map = nMap
        nMap.locationSource = locationSource
        currentMarker = Marker().apply {
            position = LatLng(nMap.cameraPosition.target.latitude, nMap.cameraPosition.target.longitude)
            map = nMap
            icon = OverlayImage.fromResource(R.drawable.ic_marker)
            width = 80
            height = 110
        }
        startLocationUpdates(nMap)
        checkIsServiceAvailable()
    }

    private fun checkIsServiceAvailable() : Boolean{
        if (!checkPermission()) {
            val permissionListener = object : PermissionListener {
                override fun onPermissionGranted() { map?.let { startLocationUpdates(it) } }
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    showToast(requireContext(), getString(R.string.permission_error_service_denied), Types.ToastType.WARNING)
                }
            }
            PermissionUtils.getLocationServicePermission(permissionListener)
        }else if (!checkLocationServicesStatus()) {
            PermissionUtils.showDialogForLocationServiceSetting(requireContext(),
                action = { activityLauncher.launch(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) },
                cancelAction = {
                    showToast(requireContext(), getString(R.string.permission_ask_service_on), Types.ToastType.WARNING)
                    moveToPopUpSelf()
                })
        }
        else return true
        return false
    }


    private fun startLocationUpdates(nMap: NaverMap) {
        binding.btnFindCurrentLocation.setOnClickListener {
            map?.let {
                it.locationSource = locationSource
                it.locationTrackingMode = LocationTrackingMode.Follow
            }
        }
        nMap.addOnCameraChangeListener { _, _ ->
            currentMarker?.position = LatLng(nMap.cameraPosition.target.latitude, nMap.cameraPosition.target.longitude)
            setButtonDisable("위치 이동 중")
        }

        nMap.addOnCameraIdleListener {
            val latLng = LatLng(nMap.cameraPosition.target.latitude, nMap.cameraPosition.target.longitude)
            currentMarker?.position = latLng
            val addressGeoDto = AddressUtils.getGeoFromPoints(requireContext(), nMap.cameraPosition.target.latitude, nMap.cameraPosition.target.longitude)
            if (addressGeoDto.type == Types.GeoAddress.ADDRESS) {
                addressDomainDto = AddressDomainDto().apply {
                    address = addressGeoDto.address
                    latitude = latLng.latitude
                    longitude = latLng.longitude
                }
                setButtonEnable(addressGeoDto.address)
            }
            else setButtonDisable(addressGeoDto.address)
        }

        mFusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let { currentPosition = LatLng(it.latitude, it.longitude) }
            nMap.locationOverlay.run {
                isVisible = true
                position = currentPosition
            }
            moveToLatLng(currentPosition)
            currentMarker?.position = LatLng(nMap.cameraPosition.target.latitude, nMap.cameraPosition.target.longitude)
        }

        binding.btnAddressAdd.setOnClickListener {
            if (isSelectionMode) viewModel.selectAddress(addressDomainDto.apply { isSelected = true })
            else viewModel.insertAddress(addressDomainDto)

        }

    }

    private fun moveToLatLng(latLng: LatLng){
        map?.moveCamera(CameraUpdate.scrollAndZoomTo(LatLng(latLng.latitude, latLng.longitude), 15.0))
    }

    private fun setButtonEnable(msg: String) {
        binding.tvAddress.text = msg
        binding.btnAddressAdd.apply {
            text = "이 위치로 주소 설정"
            isClickable = true
            setBackgroundResource(R.drawable.rectangle_blue400_radius_8)
        }
    }
    private fun setButtonDisable(msg : String) {
        binding.tvAddress.text = msg
        binding.btnAddressAdd.apply {
            text = "등록할 수 없는 주소"
            isClickable = false
            setBackgroundResource(R.drawable.rectangle_gray400_radius_8)
        }
    }

    private fun moveToPopUpSelf() { findNavController().navigate(R.id.action_addressMapFragment_pop) }
    private fun moveToPopUpToGraph() { findNavController().navigate(R.id.action_addressMapFragment_pop_including_addressGraph) }


    //--------------------------------------------------------------------------------------------------------------------------------------------------

    private val activityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode != Activity.RESULT_OK) showToast(requireContext(), getString(R.string.permission_error_service_off), Types.ToastType.ERROR)
    }


    private fun checkPermission(): Boolean {
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
        return (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED) && (hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED)
    }

    private fun checkLocationServicesStatus(): Boolean {
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER))
    }

}