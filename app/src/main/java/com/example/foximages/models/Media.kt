package com.example.foximages.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Media (
    @SerializedName("gif")
    @Expose
    val gif:Gifs? = null,
)