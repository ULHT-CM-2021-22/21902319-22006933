package com.example.cm_recurso.ui.fires_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cm_recurso.model.fire.FireParceLable
import com.example.cm_recurso.ui.repository.FireRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FiresListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FireRepository.getInstance()

    fun getAllFires(callback: (List<FireParceLable>) -> Unit){
        CoroutineScope(Dispatchers.Main).launch {
            repository.getAllFires(callback)
        }
    }

    fun getAllFiresList(): List<FireParceLable> {
        return repository.getAllFiresList()
    }

    fun getFiresByDistrict(district:String): List<FireParceLable> {
        if(district == "Todos os Distritos") {
            return getAllFiresList()
        }

        val fires : MutableList<FireParceLable> = mutableListOf()
        for(fire in getAllFiresList()) {
            if(fire.distrito == district) {
                fires.add(fire)
            }
        }

        return fires
    }
}