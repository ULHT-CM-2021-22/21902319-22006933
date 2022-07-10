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
    val man: String,
    val terrestrial: String,
    val aerial: String,
    val lat: Double,
    val lng: Double,
    val status: String,
    var isRegistry : String,
    val distance: String
) : Parcelable {

}