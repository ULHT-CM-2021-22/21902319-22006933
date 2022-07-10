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
        distance: String,
        man: String,
        terrestrial: String,
        aerial: String,
        lat: Double,
        lng: Double,
        isRegistry: String
    ) {
        val fire = FireRoom(
            fireKey = name,
            name = name,
            cartaoCidadao = cartaoCidadao,
            distrito = distrito,
            conselho = conselho,
            frequesia = frequesia,
            data = data,
            hora = hora,
            fotografia = fotografia,
            man = man,
            terrestrial = terrestrial,
            aerial = aerial,
            lat = lat,
            lng = lng,
            status = status,
            distance = distance,
            isRegistry = isRegistry)

        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(fire)
        }
    }

    override fun getAllFires(onFinished: (List<FireParceLable>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val fires = dao.getAll()
            print(fires)
            onFinished(fires.map{
                FireParceLable(
                    uuid = it.uuid,
                    fireKey = it.fireKey,
                    name = it.name,
                    cartaoCidadao = it.cartaoCidadao,
                    distrito = it.distrito,
                    conselho = it.conselho,
                    frequesia = it.frequesia,
                    data = it.data,
                    hora = it.hora,
                    fotografia = it.fotografia,
                    man = it.man,
                    terrestrial = it.terrestrial,
                    aerial = it.aerial,
                    lat = it.lat,
                    lng = it.lng,
                    status = it.status,
                    distance = it.distance,
                    isRegistry = "false"
                )
            })
        }
    }

    override fun insertFires(fires: List<FireParceLable>, onFinished: (List<FireParceLable>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val history = fires.map { FireRoom(it.uuid, it.fireKey, it.name, it.cartaoCidadao, it.distrito, it.conselho, it.frequesia, it.data, it.hora, it.fotografia, it.isRegistry, it.lng, it.lat, it.status, it.distance, it.man, it.terrestrial, it.aerial) }
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
                    uuid = it.uuid,
                    fireKey = it.fireKey,
                    name = it.name,
                    cartaoCidadao = it.cartaoCidadao,
                    distrito = it.distrito,
                    conselho = it.conselho,
                    frequesia = it.frequesia,
                    data = it.data,
                    hora = it.hora,
                    fotografia = it.fotografia,
                    man = it.man,
                    terrestrial = it.terrestrial,
                    aerial = it.aerial,
                    lat = it.lat,
                    lng = it.lng,
                    status = it.status,
                    distance = it.distance,
                    isRegistry = it.isRegistry
                )
            })
        }
    }

}