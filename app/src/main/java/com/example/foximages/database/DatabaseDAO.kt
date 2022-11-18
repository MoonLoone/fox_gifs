package com.example.foximages.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.foximages.models.DataFromAPI
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDAO{

    @Query("SELECT * FROM databaseentity")
    fun getAll(): Flow<List<DatabaseEntity>>

    @Query("SELECT * FROM databaseentity WHERE id = :id")
    suspend fun getItemByID(id: Int):DatabaseEntity

    @Insert
    suspend fun insertAll(listOfData:List<DatabaseEntity>)

    @Insert
    suspend fun insertOneItem(data:DatabaseEntity)

    @Delete
    suspend fun delete(data: DatabaseEntity)


}