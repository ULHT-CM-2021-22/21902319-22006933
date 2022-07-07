package com.example.cm_recurso.model.fire

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FireParceLable(
    var uuid: String,
    var fireKey: String,
    var name: String,
    var cartaoCidadao: String,
    var distrito: String,
    var conselho: String,
    var frequesia: String,
    var data: String,
    var hora: String,
    var fotografia: String,
    val s: String,
    val s1: String,
    val man: String,
    val terrain: String,
    val aerial: String,
    val lat: Double,
    val lng: Double,
    val s5: String,
    //var lat: Double,
    //var lng: Double,
    //var isRegistry : String = "false",
) : Parcelable {
    val isRegistry: Any
        get() {
            TODO()
        }
}