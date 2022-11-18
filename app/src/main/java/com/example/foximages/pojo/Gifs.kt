package com.example.foximages.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Gifs (
    @SerializedName("url")
    @Expose
    val url:String? = null,
    @SerializedName("dims")
    @Expose
    val dims:List<Int>? = null,
)