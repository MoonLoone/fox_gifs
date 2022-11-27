package com.example.foximages.pojo

import com.google.gson.annotations.SerializedName

data class Gifs (
    @SerializedName("url")
    val url:String? = null,
    @SerializedName("dims")
    val dims:List<Int>? = null,
)