package com.example.cm_recurso

import android.app.Application
import com.example.cm_recurso.model.fire.FireApi
import com.example.cm_recurso.model.fire.FireDataBase
import com.example.cm_recurso.model.fire.FireModelRoom
import com.example.cm_recurso.ui.repository.FireRepository
import com.example.cm_recurso.ui.location.FusedLocation
import com.example.cm_recurso.ui.repository.RetrofitBuilder

class FireMain : Application(){

    override fun onCreate() {
        super.onCreate()
        FireRepository.init(this,
            FireModelRoom(FireDataBase.getInstance(this).fireDao()),
            FireApi(RetrofitBuilder.getInstance("https://api-dev.fogos.pt"))
        )
        FusedLocation.start(this)
    }
}