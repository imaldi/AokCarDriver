package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityPendaftaranPetaBinding
import com.surelabsid.mrjempootdriver.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class PendaftaranPetaActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var binding: ActivityPendaftaranPetaBinding

    @Inject
    lateinit var sessionManager: SessionManager

    @Inject
    lateinit var gpsTracker: GPSTracker

    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendaftaranPetaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPermissionLocation()
//        sessionManager.lat_user = gpsTracker.latitude.toString()
//        sessionManager.lng_user = gpsTracker.longitude.toString()

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        initView()

    }

    private fun checkGps() {

        val locationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!isGPSEnabled) {
            showSettingGps()
        } else {
            while (sessionManager.lat_user == "0.0") {
                gpsTracker.getLocation()
                sessionManager.lat_user = gpsTracker.latitude.toString()
                sessionManager.lng_user = gpsTracker.longitude.toString()
            }

            showMarker()
        }
    }

    private fun initView() {
        with(binding) {
            buttonGunakanLokasi.setOnClickListener {
                openActivity(PendaftaranAlamatActivity::class.java)
            }

            buttonGunakanLokasi.setOnClickListener {
                if (sessionManager.flag_update) {
                    sessionManager.address_temp = showName(sessionManager.lat_address.toDouble(), sessionManager.lng_address.toDouble())
                } else {
                    sessionManager.address = showName(sessionManager.lat_address.toDouble(), sessionManager.lng_address.toDouble())
                }

                moveActivity(PendaftaranAlamatActivity::class.java)
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {

        mMap = p0
        mMap?.uiSettings?.isZoomControlsEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED == checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                checkGps()
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

        //manual pin
        mMap?.setOnCameraIdleListener {
            //cleaning all the markers
            mMap?.clear()

            sessionManager.lat_address = mMap?.cameraPosition?.target?.latitude.toString()
            sessionManager.lng_address = mMap?.cameraPosition?.target?.longitude.toString()


            binding.textViewAlamatJalan.text = showName(sessionManager.lat_address.toDouble(), sessionManager.lng_address.toDouble())

        }

    }

    private fun showName(lat: Double?, lon: Double?): String {
        var resultName = ""

        try {
            //geocoder untuk conversi koordinat jadi nama lokasi
            val geo = Geocoder(this, Locale.getDefault())

            //get array hasil berdasarkan koordinat
            val name = geo.getFromLocation(
                lat ?: 0.0,
                lon ?: 0.0, 1
            )

            resultName = name[0].getAddressLine(0)

        } catch (e: IndexOutOfBoundsException) {

        } catch (e: IOException) {
        }

        return resultName

    }

    fun showMarker() {

        val latlng = LatLng(
            sessionManager.lat_user.toDouble(),
            sessionManager.lng_user.toDouble()
        )

        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15f))
    }


    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onRestart() {
        super.onRestart()
        checkGps()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

}