package com.surelabsid.mrjempootdriver.utils

import android.Manifest
import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.surelabsid.mrjempootdriver.R

class LocationUpdaterService : Service() {

//    private var onlineRef: DatabaseReference? = null
//    private var onlineListener: ValueEventListener? = null

//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        val collectionName = intent?.getStringExtra("collection_name")
//        val idUser = intent?.getIntExtra("id_user", -1)
//        val driverName = intent?.getStringExtra("driver_name")
//
//        Log.d("LocationUpdaterService", "Service $idUser - $driverName - $collectionName")
//        val notificationIntent = Intent(this, HomeActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, "messages")
//                .setContentText("You are currently online")
//                .setContentTitle(driverName)
//                .setSmallIcon(R.drawable.common_google_signin_btn_text_dark_normal)
//                .setContentIntent(pendingIntent)
//            startForeground(8687, builder.build())
//        }
//
//        updateLocation(idUser, collectionName)
//        listenRiderOnline(idUser, collectionName)
//
//        return START_STICKY
//    }

//    private fun listenRiderOnline(idUser: Int?, collectionName: String?) {
//        val ref = FirebaseDatabase.getInstance().getReference(collectionName!!)
//        onlineRef = ref.child(idUser.toString()).child("online")
//        onlineListener = ref.child(idUser.toString()).child("online").addValueEventListener(object :
//            ValueEventListener {
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.value == false) {
//                    stopSelf()
//                }
//            }
//        })
//    }

    override fun onBind(p0: Intent?): IBinder? = null

//    override fun onDestroy() {
//        onlineListener?.let { onlineRef?.removeEventListener(it) }
//        super.onDestroy()
//    }

    private fun updateLocation(idUser: Int?, collectionName: String?) {
//        val ref = FirebaseDatabase.getInstance().getReference(collectionName!!)
        val request = LocationRequest()
        request.interval = 1000
        request.fastestInterval = 1000
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val client = LocationServices.getFusedLocationProviderClient(this)
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

//        if (permission == PackageManager.PERMISSION_GRANTED) {
//            client.requestLocationUpdates(request, object : LocationCallback() {
//                override fun onLocationResult(locationResult: LocationResult?) {
//                    val location = locationResult?.lastLocation
//                    if (location != null) {
////                        val loc = Location()
////                        loc.latitude = location.latitude;
////                        loc.longitude = location.longitude;
//                        Log.d("LocationUpdaterService", "Lat: ${loc.latitude}")
//                        Log.d("LocationUpdaterService", "Lon: ${loc.longitude}")
//
//                        if (loc.latitude != 0.0 && loc.longitude != 0.0) {
//                            //update location
////                            ref.child(idUser.toString()).child("location").setValue(loc)
//                        }
//                    }
//                }
//            }, null)
//        } else {
//            Log.d("LocationUpdaterService", "Tidak ada izin buat baca lokasi gps")
//        }

    }
}