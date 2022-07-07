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
                FireParceLable(it.id, it.district + " - " + it.concelho + " - " + it.freguesia,
                    "", "", it.district, it.concelho,
                    it.freguesia, it.date, it.hour, it.status, "", "", it.man, it.terrain, it.aerial, it.lat, it.lng, "false")
            })
        }
    }

    override fun addFire(
        nome: String,
        numeroCC: String,
        distrito: String,
        conselho: String,
        frequesia: String,
        data: String,
        hora: String,
        status: String,
        fotografia: String,
        distancia: String,
        operacionais: String,
        vehicles: String,
        planes: String,
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