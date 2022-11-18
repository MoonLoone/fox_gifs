package com.example.foximages.pojo

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
)