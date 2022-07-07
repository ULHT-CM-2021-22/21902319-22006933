package com.example.cm_recurso.model.fire

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FireModelRoom(private val dao: FireDao) : FireModel() {

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
        distancia: String,
        operacionais: String,
        vehicles: String,
        planes: String,
        lat: Double,
        lng: Double,
        isRegistry: String
    ) {
        val fire = FireRoom(
            fireKey = name, name = name, cartaoCidadao = cartaoCidadao, distrito = distrito,
            conselho = conselho, frequesia = frequesia, data = data, hora = hora, fotografia = fotografia, lng=18.1, lat=20.2, aerial = "15", terrain = "20", man = "150")

        CoroutineScope(Dispatchers.IO).launch { dao.insert(fire) }
    }

    override fun getAllFires(onFinished: (List<FireParceLable>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val fires = dao.getAll()
            print(fires)
            onFinished(fires.map{
                FireParceLable(
                    it.uuid,
                    it.fireKey,
                    it.name,
                    it.cartaoCidadao,
                    it.distrito,
                    it.conselho,
                    it.frequesia,
                    it.data,
                    it.hora,
                    it.fotografia,
                    "",
                    "",
                    it.man,
                    it.terrain,
                    it.aerial,
                    it.lat,
                    it.lng,
                    "false"
                )
            })
        }
    }

    override fun insertFires(fires: List<FireParceLable>, onFinished: (List<FireParceLable>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val history = fires.map { FireRoom(it.uuid, it.fireKey, it.name, it.cartaoCidadao, it.distrito,
                it.conselho, it.frequesia, it.data, it.hora, it.fotografia, "false", it.lng, it.lat, it.aerial, it.terrain, it.man) }
            dao.insertAll(history)
            onFinished(fires)
        }
    }

    override fun deleteAllOperations(onFinished: (List<FireParceLable>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val registeredFires : MutableList<FireRoom> = mutableListOf()
            val allFires = dao.getAll()
            for(fire in allFires){
                if(fire.isRegistry == "true"){
                    registeredFires.add(fire)
                }
            }
            dao.deleteAll()
            onFinished(registeredFires.map{
                FireParceLable(
                    it.uuid,
                    it.fireKey,
                    it.name,
                    it.cartaoCidadao,
                    it.distrito,
                    it.conselho,
                    it.frequesia,
                    it.data,
                    it.hora,
                    it.fotografia,
                    "",
                    "",
                    it.man,
                    it.terrain,
                    it.aerial,
                    it.lat,
                    it.lng,
                    "false"
                )
            })
        }
    }
}