package com.example.cm_recurso.ui.fires_map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cm_recurso.model.fire.FireParceLable
import com.example.cm_recurso.ui.repository.FireRepository
import com.google.android.gms.maps.model.LatLng

class FiresMapViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FireRepository.getInstance()

    private fun getActiveFire() : List<FireParceLable> {
        return repository.getActiveFires()
    }

    fun getGeoLocations(): List<Place> {
        val places = mutableListOf<Place>()
        val fires = getActiveFire()

        for (fire in fires) {
            places.add(Place(name = fire.name, latLng = LatLng(fire.lat, fire.lng), address = LatLng(fire.lat, fire.lng)))
        }
        return places
    }


}