package com.example.cm_recurso.model.fire

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FireRoom::class], version = 2)
abstract class FireDataBase : RoomDatabase() {

    abstract fun fireDao() : FireDao

    companion object {

        private var instance: FireDataBase? = null

        fun getInstance(applicationContext: Context): FireDataBase {
            synchronized(this) {
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        applicationContext,
                        FireDataBase::class.java,
                        "fire_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance as FireDataBase
            }
        }
    }
}