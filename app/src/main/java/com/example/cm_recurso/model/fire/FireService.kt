package com.example.cm_recurso.model.fire

import retrofit2.http.*

data class FireContent(
    val id:String,
    val date:String,
    val hour:String,
    val district: String,
    val concelho: String,
    val freguesia: String,
    val status: String,
    val man : String,
    val terrain : String,
    val aerial : String,
    val lat:Double,
    val lng:Double,
)

data class FireData(val data:List<FireContent>)

interface FireService {
    @GET("/new/fires")
    suspend fun getAllFires():FireData
}