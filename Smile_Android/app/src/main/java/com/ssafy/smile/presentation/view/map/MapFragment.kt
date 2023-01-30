package com.ssafy.smile.presentation.view.map

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.gun0912.tedpermission.PermissionListener
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.MapFragment
import com.naver.maps.map.util.FusedLocationSource
import com.ssafy.smile.R
import com.ssafy.smile.common.util.PermissionUtils
import com.ssafy.smile.databinding.FragmentMapBinding
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment

class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::bind, R.layout.fragment_map),
    OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private const val UPDATE_INTERVAL = 1000
        private const val FASTEST_UPDATE_INTERVAL = 500
    }

    private var map: NaverMap? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var currentPosition: LatLng = LatLng(37.56, 126.97)
    private var locationSource : FusedLocationSource ? = null

    override fun initView() {
        (childFragmentManager.findFragmentById(R.id.mapView) as MapFragment).getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun setEvent() {
        //TODO("Not yet implemented")
    }

    private fun moveToAddressGraph() = findNavController().navigate(R.id.action_mainFragment_to_addressGraph)

    override fun onMapReady(naverMap: NaverMap) {
        with(naverMap) {
            uiSettings.isLocationButtonEnabled = false
            uiSettings.isZoomGesturesEnabled = true
            uiSettings.isZoomControlEnabled = false
            locationTrackingMode = LocationTrackingMode.Follow
        }
        this.map = naverMap
        naverMap.locationSource = locationSource
        startLocationUpdates(naverMap)
        checkIsServiceAvailable()
    }

    private fun checkIsServiceAvailable() : Boolean{
        if (!checkPermission()) {
            val permissionListener = object : PermissionListener {
                override fun onPermissionGranted() { }
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    showToast(requireContext(), getString(R.string.permission_error_service_denied), Types.ToastType.WARNING)
                }
            }
            PermissionUtils.getLocationServicePermission(permissionListener)
        }else if (!checkLocationServicesStatus()) {
            PermissionUtils.showDialogForLocationServiceSetting(requireContext(),
                action = { activityLauncher.launch(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) },
                cancelAction = {
                    showToast(requireContext(), "위치 서비스를 활성화하지 않으면\n지도를 사용하실 수 없습니다.", Types.ToastType.WARNING)
                })
        }
        else return true
        return false
    }

    private fun startLocationUpdates(naverMap: NaverMap) {
//        binding.btnFindCurrentLocation.setOnClickListener {
//            map?.let {
//                it.locationSource = locationSource
//                it.locationTrackingMode = LocationTrackingMode.Follow
//            }
//        }

        naverMap.addOnCameraChangeListener { _, _ ->
            //currentMarker?.position = LatLng(naverMap.cameraPosition.target.latitude, naverMap.cameraPosition.target.longitude)
            //makeButtonUnEnable("위치 이동 중")
        }

        naverMap.addOnCameraIdleListener {
//            val latLng = LatLng(naverMap.cameraPosition.target.latitude, naverMap.cameraPosition.target.longitude)
//            currentMarker?.position = latLng
//            val addressGeoDto = AddressUtils.getGeoFromPoints(requireContext(), naverMap.cameraPosition.target.latitude, naverMap.cameraPosition.target.longitude)
//            if (addressGeoDto.type == AddressGeoDto.GeoAddress.ADDRESS) makeButtonEnable(addressGeoDto.address, latLng)
//            else makeButtonUnEnable(addressGeoDto.address)
        }

        mFusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let { currentPosition = LatLng(it.latitude, it.longitude) }
            naverMap.locationOverlay.run {
                isVisible = true
                position = currentPosition
            }
            moveToLatLng(currentPosition)
            //currentMarker?.position = LatLng(naverMap.cameraPosition.target.latitude, naverMap.cameraPosition.target.longitude)
        }


    }

    private fun moveToLatLng(latLng: LatLng){
        map?.moveCamera(CameraUpdate.scrollAndZoomTo(LatLng(latLng.latitude, latLng.longitude), 15.0))
    }

    //--------------------------------------------------------------------------------------------------------------------------------

    private val activityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode != Activity.RESULT_OK) showToast(requireContext(), getString(R.string.permission_error_service_off, Types.ToastType.WARNING))
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