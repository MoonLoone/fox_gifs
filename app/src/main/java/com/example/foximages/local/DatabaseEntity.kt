package com.example.foximages.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "id_from_api")
    val apiId: String? = null,
    val contentDescription: String? = null,
    val url: String? = null,
    val width: Int? = null,
    val height: Int? = null

)
