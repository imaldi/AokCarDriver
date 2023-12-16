package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityPendaftaranAlamatBinding
import com.surelabsid.mrjempootdriver.utils.Constant
import com.surelabsid.mrjempootdriver.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PendaftaranAlamatActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var binding: ActivityPendaftaranAlamatBinding

    @Inject
    lateinit var sessionManager: SessionManager

    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendaftaranAlamatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        iniView()

    }

    private fun iniView() {

        with(binding) {

            textViewAlamatJalanMaps.text = sessionManager.address

            editTextDetailAlamat.doAfterTextChanged {
                sessionManager.address_detail = it.toString()
            }

            simpanAlamat.setOnClickListener {
                finish()
            }

        }

    }

    override fun onMapReady(p0: GoogleMap) {

        mMap = p0
        mMap?.isMyLocationEnabled = true
        mMap?.uiSettings?.isZoomControlsEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED == checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                showMarker()
            } else {
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ), Constant.Companion.permission.ACCESS_LOCATION
                )
            }
        } else {
            //
        }

    }

    fun showMarker() {

        val latlng = LatLng(
            sessionManager.lat_address.toDouble(),
            sessionManager.lng_address.toDouble()
        )

        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15f))
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }



}