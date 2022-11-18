package com.example.foximages.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class DataFromAPI(
    @SerializedName("id")
    @Expose
    val id:String? = null,
    @SerializedName("content_description")
    @Expose
    val contentDescription:String? = null,
    @SerializedName("media")
    @Expose
    val media:List<Media>? = null,
    @SerializedName("next")
    @Expose
    val next:Int? = null,
)