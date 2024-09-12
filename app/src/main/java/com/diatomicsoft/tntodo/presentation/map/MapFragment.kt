package com.diatomicsoft.tntodo.presentation.map

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.diatomicsoft.tntodo.R
import com.diatomicsoft.tntodo.base.BaseFragment
import com.diatomicsoft.tntodo.databinding.FragmentMapBinding
import com.diatomicsoft.tntodo.utils.PermissionManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map), OnMapReadyCallback {

    private val viewModel: MapViewModel by viewModels()
    private lateinit var map: GoogleMap
    private lateinit var permissionManager: PermissionManager
    private val LOCATION_PERMISSION_REQUEST_CODE = 2001

    // Permissions to request
    private val locationPermissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionManager = PermissionManager(this)

        mBinding.viewModel = viewModel

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        observeLocationUpdates()
    }


    private fun observeLocationUpdates() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentLocation.collect { location ->
                    // Handle location updates
                    location?.let {
                        moveCamera(LatLng(it.latitude, it.longitude))
                    }

                }
            }
        }

    }

    private fun requestAndHandlePermission() {
        // Request location permissions
        permissionManager.requestPermissions(
            locationPermissions,
            LOCATION_PERMISSION_REQUEST_CODE,
            object : PermissionManager.PermissionCallback {
                override fun onGranted() {
                    viewModel.requestLocationUpdates()
                }

                override fun onDenied(deniedPermissions: List<String>) {
                    // Handle denied permissions
                    Toast.makeText(
                        requireContext(),
                        "Location Permissions Denied: $deniedPermissions",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        moveCamera(LatLng(23.8087023,90.4185115))

        requestAndHandlePermission()
    }

    private fun moveCamera(latLng: LatLng) {
        Log.d("MyMap", "lat: ${latLng.latitude}, lng: ${latLng.longitude}")
        map.addMarker(MarkerOptions().position(latLng).title("TechnoNext"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
    }
}