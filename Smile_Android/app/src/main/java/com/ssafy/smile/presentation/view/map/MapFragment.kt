package com.ssafy.smile.presentation.view.map

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.PointF
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.gun0912.tedpermission.PermissionListener
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.ssafy.smile.R
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.util.PermissionUtils
import com.ssafy.smile.data.remote.model.ClusterDto
import com.ssafy.smile.databinding.FragmentMapBinding
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseFragment
import com.ssafy.smile.presentation.view.MainFragmentDirections
import com.ssafy.smile.presentation.viewmodel.map.MapViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "MapFragment_스마일"
class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::bind, R.layout.fragment_map),
    OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private val viewModel : MapViewModel by viewModels()

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private const val UPDATE_INTERVAL = 1000
        private const val FASTEST_UPDATE_INTERVAL = 500

        private val DeviceWidth: Int = Resources.getSystem().displayMetrics.widthPixels
        private val DeviceHeight: Int = Resources.getSystem().displayMetrics.heightPixels
    }

    private var map: NaverMap? = null
    private var locationSource : FusedLocationSource ? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var currentPosition: LatLng = LatLng(37.56, 126.97)
    private var presentLatLngBounds : Pair<LatLng, LatLng>?= null
    private val clusterList : ArrayList<ClusterDto> = arrayListOf()
    private val markerList : ArrayList<Marker> = arrayListOf()
    private var isInitialized : Boolean = false

    override fun onResume() {
        super.onResume()
        checkIsServiceAvailable()
    }

    override fun initView() {
        (childFragmentManager.findFragmentById(R.id.mapView) as MapFragment).getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun setEvent() {
        setObserver()
        setClickListener()
    }

    private fun setObserver() {
        viewModel.apply {
            getArticleClusterInfoResponse.observe(viewLifecycleOwner){
                when(it) {
                    is NetworkUtils.NetworkResponse.Loading -> {
                    }
                    is NetworkUtils.NetworkResponse.Success -> {
                        Log.d("스마일", "setObserver: ${it.data}")
                        if (isInitialized && it.data.isEmpty()) binding.tvFindNoMarker.visibility = View.VISIBLE
                        else {
                            binding.tvFindNoMarker.visibility = View.GONE
                            updateClusterInfo(it.data)
                            lifecycleScope.launch(Dispatchers.Main){
                                map?.let { nMap -> updateMarkerInfo(nMap, clusterList) }
                            }
                        }
                        isInitialized = true
                    }
                    is NetworkUtils.NetworkResponse.Failure -> {
                    }
                }
            }
        }
    }

    private fun setClickListener(){
        binding.apply {
            btnFindArticles.setOnClickListener {
                presentLatLngBounds?.let { viewModel.getPhotographerInfo(it.first, it.second) }
                showToast(requireContext(), "게시글 정보를 재검색합니다.", Types.ToastType.BASIC)
            }
            btnFindCurrentLocation.setOnClickListener {
                map?.let {
                    it.locationSource = locationSource
                    it.locationTrackingMode = LocationTrackingMode.Follow
                }
            }
        }
    }

    private fun updateClusterInfo(clusterDtoList : List<ClusterDto>){
        clusterList.clear()
        clusterList.addAll(clusterDtoList)
    }

    private fun updateMarkerInfo(nMap: NaverMap, clusterDtoList : List<ClusterDto>){
        for (marker in markerList){ marker.map = null }
        markerList.clear()
        for (cluster in clusterDtoList){
            val marker = Marker().apply {
                captionText = cluster.numOfCluster.toString()+"개"
                position = LatLng(cluster.centroidLat, cluster.centroidLng)
                icon = OverlayImage.fromResource(R.drawable.ic_marker)
                setOnClickListener {
                    val action = MainFragmentDirections.actionMainFragmentToMapListFragment(cluster.clusterId)
                    findNavController().navigate(action)
                    true
                }
                width = 90
                height = 120
                map = nMap
            }
            markerList.add(marker)
        }
    }

    override fun onMapReady(nMap: NaverMap) {
        with(nMap) {
            uiSettings.isLocationButtonEnabled = false
            uiSettings.isZoomGesturesEnabled = true
            uiSettings.isZoomControlEnabled = false
            uiSettings.isRotateGesturesEnabled = false
            minZoom = 6.0
            maxZoom = 15.0
            locationTrackingMode = LocationTrackingMode.Follow
            addOnCameraIdleListener {
                presentLatLngBounds = getBoundsLatLng(nMap)
                presentLatLngBounds?.let { viewModel.getPhotographerInfo(it.first, it.second) }
            }
        }
        this.map = nMap
        nMap.locationSource = locationSource
        if (checkIsServiceAvailable() && !isInitialized) startLocationUpdates(nMap)
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
                    showToast(requireContext(), "위치 서비스를 활성화하지 않으면\n지도를 사용하실 수 없습니다.", Types.ToastType.WARNING)
                })
        }
        else return true
        return false
    }


    private fun startLocationUpdates(nMap: NaverMap) {
        mFusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let { currentPosition = LatLng(it.latitude, it.longitude) }
            nMap.locationOverlay.run {
                isVisible = true
                position = currentPosition
            }
            moveToLatLng(currentPosition)
        }
    }

    private fun moveToLatLng(latLng: LatLng){
        map?.moveCamera(CameraUpdate.scrollAndZoomTo(LatLng(latLng.latitude, latLng.longitude), 15.0))
    }

    private fun getBoundsLatLng(nMap: NaverMap, width: Float = DeviceWidth.toFloat(), height: Float = DeviceHeight.toFloat()): Pair<LatLng, LatLng> {
        val projection: Projection  = nMap.projection
        val northWest = projection.fromScreenLocation(PointF(0F, 0F))
        val southEast = projection.fromScreenLocation(PointF(width, height))
        return Pair(northWest, southEast)
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