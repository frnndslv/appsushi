package com.example.oxentesushi.data

import android.app.TaskInfo
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SushiDao{
    @Query("SELECT * FROM sushis")
    fun getAllSushi(): LiveData<List<Sushi>>

    @Insert
    suspend fun insertSushi(sushi: Sushi)

    @Update
    suspend fun updateSushi(sushi: Sushi)

    @Delete
    suspend fun deleteSushi(sushi : Sushi)

    @Query("SELECT * FROM sushis WHERE id = :id")
    fun getSushiById(id: Int): LiveData<Sushi>

}