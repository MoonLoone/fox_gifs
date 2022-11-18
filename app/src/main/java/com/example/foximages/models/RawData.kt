package com.example.foximages.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RawData(
    @SerializedName("results")
    @Expose
    val results:List<DataFromAPI>? = null,
){

}

