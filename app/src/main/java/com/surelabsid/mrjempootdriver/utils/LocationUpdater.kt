package com.surelabsid.mrjempootdriver.utils

import android.content.Context
import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.maps.model.LatLng
import com.surelabsid.mrjempootdriver.network.ApiService
import com.surelabsid.mrjempootdriver.network.ApiServiceVerihubs
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestUpdateLocation
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.ResponseUpdateLocation
import com.surelabsid.mrjempootdriver.ui.beranda.viewmodel.HomeViewModel
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.location.INTERVAL_UPDATE_LOCATION
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Runnable
import javax.inject.Inject

class LocationUpdater @Inject constructor(
    private val api: ApiService?,
    private val compositeDisposable: CompositeDisposable?,
    val context: Context
) {
    private val gps: GPSTracker = GPSTracker(context)
    private val sesi: SessionManager = SessionManager(context)
    private var onWorkingUpdateLocation = false
    private var locationListener: LocationListener? = null

    private lateinit var locationCallback: LocationCallback

    private val lock = Object()
    private var isLocked = false
    private var isStop = false
    private var thread: Thread? = null

    companion object {
        @Volatile
        private var INSTANCE: LocationUpdater? = null

        @JvmStatic
        fun getInstance(context: Context, onNew: (LocationUpdater?) -> Unit): LocationUpdater {
            INSTANCE = INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocationUpdater(null, null, context).also {
                    INSTANCE = it
                }
            }
            onNew(INSTANCE)
            return INSTANCE as LocationUpdater
        }
    }

    private fun updateLocationDriver() {

        if (gps.canGetLocation != false) {
            // update in SessionManager
            sesi.let { sessionManager ->
                sessionManager.lat_user = gps.latitude.toString()
                sessionManager.lng_user = gps.longitude.toString()
            }
            // update location driver in FirebaseDatabase
        //            comment dulu testing
//            val param = RequestUpdateLocation(sesi.id, sesi.lat_user, sesi.lng_user)
//            repoUpdateLocation(param, {
//                Log.d("TAG", "updateLocationDriver: ${it.message}")
//            }, {
//                Log.d("TAG", "updateLocationDriver: ${it.message}")
//            })
        } else {
            sesi.let { sessionManager ->
                sessionManager.lat_user = "0.0"
                sessionManager.lng_user = "0.0"
            }
            // update in FirebaseDatabase
            // databaseReference?.child("${sesi?.idUser}")?.child("location")?.setValue(loc)

//            comment dulu testing
//            val param = RequestUpdateLocation(sesi.id, sesi.lat_user, sesi.lng_user)
//            repoUpdateLocation(param, {
//                Log.d("TAG", "updateLocationDriver: ${it.message}")
//            }, {
//                Log.d("TAG", "updateLocationDriver: ${it.message}")
//            })

            locationListener?.onFailedGetNewLocation()
        }
        locationListener?.onNewLocation(LatLng(sesi.lat_user.toDouble(), sesi.lng_user.toDouble()))
    }

    fun addLocationListener(locationListener: LocationListener) {
        this.locationListener = locationListener
    }

    fun start() {
        if (!onWorkingUpdateLocation) { // do not invoke multiple time
            onWorkingUpdateLocation = !onWorkingUpdateLocation

            thread = Thread(Runnable {
                while (!isStop) {
                    try {
                        updateLocationDriver()
                        Thread.sleep(INTERVAL_UPDATE_LOCATION.times(10000).toLong())
                        if (isLocked) {
                            synchronized(lock) {
                                lock.wait()
                            }
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })
            thread?.start()
        }
    }

    fun pause() {
        isLocked = true
    }

    fun resume() {
        try {
            if (isLocked) {
                isLocked = false
                synchronized(lock) {
                    lock.notify()
                }
            }
        } catch (e: Exception) {
        }
    }

    fun clear() {
        try {
            onWorkingUpdateLocation = false
            if (isLocked) {
                isLocked = false
                synchronized(lock) {
                    lock.notify()
                }
            }
            isStop = true
            INSTANCE = null
        } catch (e: Exception) {
        }
    }

    fun repoUpdateLocation(
        param: RequestUpdateLocation,
        response_handler: (ResponseUpdateLocation) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable?.add(
            api?.apiUpdateLocation(param)?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                }) ?: Disposable.empty()
        )

    }

    interface LocationListener {
        fun onNewLocation(location: LatLng?) {}
        fun onFailedGetNewLocation() {}
    }
}