package com.example.foximages.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RawData(
    @SerializedName("results")
    @Expose
    val results:List<DataFromApi> = emptyList(),
    @SerializedName("next")
    @Expose
    val next:Int? = null,
)

