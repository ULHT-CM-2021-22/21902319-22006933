package com.example.cm_recurso.model.fire

abstract class FireModel {
    //Funções utilizadas em FireRoomModel e FireRetrofit
    abstract fun addFire(
        nome: String, numeroCC: String, distrito: String,
        conselho: String, frequesia: String, data: String, hora: String, status: String, fotografia: String,
        distancia: String, operacionais: String, vehicles: String, planes: String, lat: Double, lng: Double, isRegistry: String)
    abstract fun getAllFires(onFinished: (List<FireParceLable>) -> Unit)
    abstract fun insertFires(fires: List<FireParceLable>, onFinished: (List<FireParceLable>) -> Unit)
    abstract fun deleteAllOperations(onFinished: (List<FireParceLable>) -> Unit)
}