package com.example.foximages.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val gifs: DatabaseDAO
}