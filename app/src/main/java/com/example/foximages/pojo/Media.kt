package com.example.foximages.pojo

import com.google.gson.annotations.SerializedName

data class Media (
    @SerializedName("gif")
    val gif:Gifs? = null,
)