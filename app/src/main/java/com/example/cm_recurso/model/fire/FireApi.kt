package com.example.cm_recurso.model.fire

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class FireApi(retrofit: Retrofit) : FireModel() {

    private val TAG = FireApi::class.java.simpleName
    private val service = retrofit.create(FireService::class.java)


    override fun getAllFires(onFinished: (List<FireParceLable>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val fires = service.getAllFires()
            onFinished(fires.data.map{
                FireParceLable(
                    uuid = it.id,
                    fireKey = "",
                    name = "",
                    cartaoCidadao = "",
                    distrito = it.district,
                    conselho = it.concelho,
                    frequesia = it.freguesia,
                    data = it.date,
                    hora = it.hour,
                    fotografia = "",
                    man = it.man,
                    terrestrial = it.terrain,
                    aerial = it.aerial,
                    lat = it.lat,
                    lng = it.lng,
                    status = it.status,
                    distance = ""
                )
            })
        }
    }

    //
    override fun addFire(
        name: String,
        cartaoCidadao: String,
        distrito: String,
        conselho: String,
        frequesia: String,
        data: String,
        hora: String,
        status: String,
        fotografia: String,
        distance: String,
        man: String,
        terrestrial: String,
        aerial: String,
        lat: Double,
        lng: Double,
        isRegistry: String
    ) {
        TODO("Not yet implemented")
    }

    override fun insertFires(fires: List<FireParceLable>,
                             onFinished: (List<FireParceLable>) -> Unit) {
    }

    override fun deleteAllOperations(onFinished: (List<FireParceLable>) -> Unit) {
    }
}