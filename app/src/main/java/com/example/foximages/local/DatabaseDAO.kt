package com.example.foximages.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DatabaseDao {
    @Query("SELECT * FROM databaseentity")
    fun getAll(): PagingSource<Int, DatabaseEntity>

    @Query("SELECT * FROM databaseentity WHERE id = :id")
    suspend fun get(id: Int): DatabaseEntity

    @Insert
    suspend fun insert(listOfData: List<DatabaseEntity>)

    @Insert
    suspend fun insert(data: DatabaseEntity)

    @Delete
    suspend fun delete(data: DatabaseEntity)

    @Query("DELETE FROM databaseentity")
    suspend fun clear()

    @Query("SELECT COUNT(*) FROM databaseentity")
    suspend fun count(): Long
}