package com.example.foximages.pojo

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

@Immutable
data class DataFromApi(
    @SerializedName("id")
    val id:String? = null,
    @SerializedName("content_description")
    val contentDescription:String? = null,
    @SerializedName("media")
    val media:List<Media>? = null,
)