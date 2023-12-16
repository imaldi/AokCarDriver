package com.surelabsid.mrjempootdriver.utils

import android.Manifest
import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat

class GPSTracker(c: Context) : Service(), LocationListener {
    private var context: Context? = null
    private var isGPSEnabled = false
    private var isNetworkEnabled = false
    internal var canGetLocation = false
    internal var location: Location? = null
    internal var latitude: Double = 0.toDouble()
    internal var longitude: Double = 0.toDouble()
    internal var session: SessionManager? = null
    private var locationManager: LocationManager? = null

    init {
        this.context = c
        getLocation()
    }

    companion object {
        private const val MIN_DISTANCE = 0f
        private const val MIN_TIME = (10000 * 1 * 1).toLong() // 1minute

        fun checkPermission(context: Context?): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                ActivityCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
        }
    }

    fun getLocation(): Location? {
        try {
            locationManager =
                context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)

            isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGPSEnabled && !isNetworkEnabled) {
//                showSettingGps()
                session?.gps = false

            } else {
                canGetLocation = true

                if (isGPSEnabled) {
                    if (location == null) {
                        if (checkPermission(context)) {
                            locationManager!!.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER, MIN_TIME,
                                MIN_DISTANCE, this
                            )
                            Log.d("GPS", "GPS enabled")
                            if (locationManager != null) {
                                location = locationManager!!
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                                if (location != null) {
                                    val locationPoint = location as Location
                                    latitude = locationPoint.latitude
                                    longitude = locationPoint.longitude

                                    session?.lat_user = locationPoint.latitude.toString()
                                    session?.lng_user = locationPoint.longitude.toString()
                                }
                            }
                        }
                    }
                } else if (isNetworkEnabled) {
                        if (checkPermission(context)) {
                            locationManager!!.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER, MIN_TIME,
                                MIN_DISTANCE, this
                            )
                            Log.d("network", "network enabled")
                            if (locationManager != null) {
                                location = locationManager!!
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                                if (location != null) {
                                    val locationPoint = location as Location
                                    latitude = locationPoint.latitude
                                    longitude = locationPoint.longitude

                                    session?.lat_user = locationPoint.latitude.toString()
                                    session?.lng_user = locationPoint.longitude.toString()
                                }
                            }
                        }
                } else {
//                    showSettingGps()

                    Log.d("TAG", "getLocation: kesini")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return location
    }

    override fun onLocationChanged(p0: Location) {
        Log.d(
            "LocationChanged",
            "onLocationChanged: ${p0.latitude},${p0.longitude}"
        )
        if (this.location !== p0) {
            this.latitude = p0.latitude
            this.longitude = p0.longitude
        }
    }

    /**
     * Function to show settings alert dialog On pressing Settings button will
     * lauch Settings Options
     */
//    fun showSettingGps() {
//        val alertBuilder = AlertDialog.Builder(context)
//        alertBuilder.setTitle("GPS Setting")
//        alertBuilder.setMessage("GPS is not enabled. Do you want to go to settings menu?")
//        alertBuilder.setPositiveButton("Setting") { _, _ ->
//            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            context?.startActivity(intent)
//        }
//        alertBuilder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
//
//        alertBuilder.show()
//    }


    override fun onProviderDisabled(provider: String) {

    }

    override fun onProviderEnabled(provider: String) {
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


}