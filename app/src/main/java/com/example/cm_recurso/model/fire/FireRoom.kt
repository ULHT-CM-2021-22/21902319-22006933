package com.example.cm_recurso.model.fire

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "fire")
data class FireRoom(
    @PrimaryKey val uuid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "fire") val fireKey: String,
    var name: String,
    var cartaoCidadao: String,
    var distrito: String,
    var conselho: String,
    var frequesia: String,
    var data: String,
    var hora: String,
    var fotografia: String,
    var isRegistry : String = "false",
    val lng: Double,
    val lat: Double,
    val aerial: String,
    val terrain: String,
    val man: String
) {

    override fun toString(): String {
        return "name='$name', " +
                "cartaoCidadao=$cartaoCidadao, " +
                "destrito='$distrito', " +
                "data=$data, " +
                "hora='$hora', " +
                "fotografia='$fotografia')"
    }
}
