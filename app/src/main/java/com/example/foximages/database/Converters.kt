package com.example.foximages.database

import androidx.room.TypeConverter
import com.example.foximages.models.Media

class Converters {
    @TypeConverter
    fun fromMedia(list:List<Media>) = list.map { it.gif }.map { it?.url }

    @TypeConverter
    fun toMedia(data:Media){

    }
}