package com.example.cm_recurso.ui.fires_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cm_recurso.model.fire.FireParceLable

import com.example.cm_recurso.ui.repository.FireRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FiresListViewModel(application: Application) : AndroidViewModel(application){

    private val repository = FireRepository.getInstance()
    private var listOrganize: List<FireParceLable> = listOf()

    fun getAllFires(callback: (List<FireParceLable>) -> Unit){
        CoroutineScope(Dispatchers.Main).launch {
            repository.getAllFires(callback)
        }
    }

    fun getAllFiresList(): List<FireParceLable> {
        return repository.getAllFiresList()
    }

    fun getFiresByDistrict(district:String, organize: String, currentLat: Double, curranteLng: Double): List<FireParceLable> {

        if(district.equals("Todos os Distritos")) {
            if (organize.equals("Decrescente")) {
                organizeDecrescente(currentLat, curranteLng)
            }else{
                organizeCrescente(currentLat,curranteLng)
            }
            return getListOrganize()
        }

        val fires : MutableList<FireParceLable> = mutableListOf()
        for(fire in getAllFiresList()) {
            if(fire.distrito.equals(district)) {
                fires.add(fire)
                setListOrganize(fires)
                if (organize.equals("Decrescente")) {
                    organizeDecrescente(currentLat, curranteLng)
                }else{
                    organizeCrescente(currentLat,curranteLng)
                }
            }
        }

        return getListOrganize()
    }

    fun setListOrganize(list: List<FireParceLable>) {
        listOrganize = list
    }

    fun getListOrganize(): List<FireParceLable> {
        return listOrganize
    }


    fun organizeCrescente(currentLat:Double, curranteLng:Double) {
        if (getListOrganize().isEmpty()) {
            setListOrganize(getAllFiresList())
        }
        var mapAux: MutableMap<Double,FireParceLable> = mutableMapOf()

        for (fire in getListOrganize()) {
            val distance = defineDistance(currentLat, curranteLng, fire.lat, fire.lng)
            mapAux.set(distance,fire)
        }

        val sorted = mapAux.toSortedMap()
        val listAux : MutableList<FireParceLable> = mutableListOf()

        for (fire in sorted) {
            listAux.add(fire.value)
        }

        setListOrganize(listAux)
    }

    fun organizeDecrescente(currentLat: Double, curranteLng: Double) {
        if (getListOrganize().isEmpty()) {
            setListOrganize(getAllFiresList())
        }

    }

    fun defineDistance(userLat:Double, userLng:Double, fireLat:Double, fireLng:Double):Double {
        var earthRadiusKm = 6371;

        var dLat = degreesToRadians(userLat-fireLat)
        var dLon = degreesToRadians(userLng-fireLng)

        var convertedUserLat = degreesToRadians(userLat)
        var convertedFireLat = degreesToRadians(fireLat)

        var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(convertedUserLat) * Math.cos(convertedFireLat)
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))
        return earthRadiusKm * c
    }

    fun degreesToRadians(degrees:Double):Double {
        return degrees * Math.PI / 180;
    }

}