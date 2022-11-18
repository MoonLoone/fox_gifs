package com.example.foximages.database

import android.content.Context
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun dao():DatabaseDAO

    companion object{
        private var dbInstance:AppDatabase? = null
        fun getInstance(context: Context): AppDatabase{
            if(dbInstance == null){
                synchronized(AppDatabase::class){
                    dbInstance = Room.databaseBuilder(context, AppDatabase::class.java, "gifsDB").build()
                }
            }
            return dbInstance!!
        }
        fun deleteDB(){
            dbInstance = null
        }
    }
}