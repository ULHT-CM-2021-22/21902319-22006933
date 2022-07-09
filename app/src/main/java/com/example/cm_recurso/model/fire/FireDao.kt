package com.example.cm_recurso.model.fire

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FireDao {

    @Insert
    suspend fun insert(fire: FireRoom)

    @Insert
    suspend fun insertAll(fire: List<FireRoom>)

    @Query("SELECT * FROM fire")
    suspend fun getAll(): List<FireRoom>

    @Query("DELETE FROM fire")
    suspend fun deleteAll(): Int

    @Delete
    suspend fun deleteList(fire: List<FireRoom>)

}