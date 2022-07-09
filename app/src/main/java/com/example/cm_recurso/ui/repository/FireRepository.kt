package com.example.cm_recurso.ui.repository

import android.content.Context
import com.example.cm_recurso.model.fire.FireModel
import com.example.cm_recurso.model.fire.FireParceLable

class FireRepository private constructor(private val context: Context,
                                         private val local: FireModel,
                                         private val remote: FireModel) {

    private var fires : List<FireParceLable> = emptyList()

    fun getAllFires(onFinished: (List<FireParceLable>) -> Unit) {
        if(ConnectivityUtil.isOnline(context)) {
            remote.getAllFires { remote ->
                local.deleteAllOperations { registers ->
                    local.insertFires(remote + registers) {
                        fires = remote + registers
                        onFinished(remote + registers)
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

    fun getAllFiresList() : List<FireParceLable>{
        return fires
    }

    fun getActiveFires() : List<FireParceLable>{
        return fires
    }

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