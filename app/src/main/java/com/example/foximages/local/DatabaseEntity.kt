package com.example.foximages.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val id_from_api: String? = null,
    val contentDescription:String? = null,
    val url:String? = null,
    val width: Int? = null,
    val height: Int? = null

)
