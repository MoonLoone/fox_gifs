package com.example.foximages.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Media (
    @SerializedName("gif")
    @Expose
    val gif:Gifs? = null,
)