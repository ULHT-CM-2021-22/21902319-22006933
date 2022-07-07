package com.example.cm_recurso.ui.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*


@SuppressLint("MissingPermission")
class FusedLocation private constructor(context: Context) : LocationCallback() {

    private val TAG = FusedLocation::class.java.simpleName

    private val TIME_BETWEEN_UPDATES = 1 * 1000L

    private var locationRequest = com.google.android.gms.location.LocationRequest.create().apply {
        priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = TIME_BETWEEN_UPDATES
    }

    private var client = FusedLocationProviderClient(context)

    init {
        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()

        LocationServices.getSettingsClient(context)
            .checkLocationSettings(locationSettingsRequest)

        Looper.myLooper()?.let { client.requestLocationUpdates(locationRequest, this, it) }
    }

    override fun onLocationResult(locationResult: LocationResult) {
        Log.i(TAG, locationResult.lastLocation.toString())
        notifyListener(locationResult)
    }

    companion object {
        private val listeners = mutableListOf<OnLocationChangedListener>()
        private var instance: FusedLocation? = null

        fun registerListener(listener: OnLocationChangedListener) {
            listeners.add(listener)
        }

        fun unregisterListener(listener: OnLocationChangedListener) {
            listeners.remove(listener)
        }

        fun notifyListener(locationResult: LocationResult) {
            val location = locationResult.lastLocation
            listeners.forEach {
                if (location != null) {
                    it.onLocationChanged(location.latitude, location.longitude)
                }
            }
        }

        fun start(context: Context) {
            instance = if (instance == null) FusedLocation(context) else instance
        }
    }

}