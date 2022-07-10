package com.example.cm_recurso.ui.fires_map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cm_recurso.model.fire.FireParceLable
import com.example.cm_recurso.ui.repository.FireRepository
import com.google.android.gms.maps.model.LatLng

class FiresMapViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FireRepository.getInstance()

    fun getActiveFire() : List<FireParceLable> {
        return repository.getActiveFires()
    }

}