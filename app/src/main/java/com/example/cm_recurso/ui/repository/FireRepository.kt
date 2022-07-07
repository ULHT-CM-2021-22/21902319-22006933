package com.example.cm_recurso.ui.repository

import android.content.Context
import com.example.cm_recurso.model.fire.FireModel
import com.example.cm_recurso.model.fire.FireParceLable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FireRepository private constructor(private val context: Context,
                                         private val local: FireModel,
                                         private val remote: FireModel) {

    private var fires : List<FireParceLable> = emptyList()


    fun getAllFires(onFinished: (List<FireParceLable>) -> Unit) {
        if(ConnectivityUtil.isOnline(context)) {
            remote.getAllFires { history ->
                local.deleteAllOperations { registers ->
                    local.insertFires(history + registers) {
                        fires = history + registers
                        onFinished(history + registers)
                    }
                }
            }
        } else {
            local.getAllFires{ history ->
                fires = history
                onFinished(history)
            }
        }
    }

    fun getAllRegistos(onFinished: (List<FireParceLable>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            onFinished(fires)
        }
    }

    fun getAllFiresList() : List<FireParceLable>{
        return fires
    }

    fun deleteFire(fire: FireParceLable, onSucess: (List<FireParceLable>) -> Unit) {
        val firesFiltered : MutableList<FireParceLable> = mutableListOf()

        for(fireInList in fires){
            if(fireInList.uuid != fire.uuid && fireInList.isRegistry == "true"){
                firesFiltered.add(fireInList)
            }
        }

        local.deleteAllOperations {
            local.insertFires(firesFiltered) {
                onSucess(firesFiltered)
            }
        }
    }

    //Funções para estatisticas
    fun getActiveFires() : List<FireParceLable>{
        return fires
    }

    fun getDistrictWithMostFires() : String {
        val firesByDistrict : MutableMap<String, Int> = mutableMapOf()
        var districtWithMostFiresName = "Distrito"
        var districtWithMostFiresValue = 0

        for (f in fires) {
            if(firesByDistrict.containsKey(f.distrito)) {
                firesByDistrict.put(f.distrito, firesByDistrict.getValue(f.distrito) + 1)
            } else {
                firesByDistrict.put(f.distrito, 1)
            }
        }

        for(key in firesByDistrict.keys) {
            if(firesByDistrict[key]!! > districtWithMostFiresValue) {
                districtWithMostFiresName = key
                districtWithMostFiresValue = firesByDistrict[key]!!
            }
        }

        return districtWithMostFiresName
    }

    //Singleton
    companion object {

        private var instance: FireRepository? = null

        fun init(context: Context, local: FireModel, remote: FireModel) {
            synchronized(this) {
                if(instance == null) {
                    instance = FireRepository(context, local, remote)
                }
            }
        }

        fun getInstance(): FireRepository {
            return instance ?: throw IllegalStateException("Repository not initialized")
        }
    }
}