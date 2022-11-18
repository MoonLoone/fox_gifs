package com.example.foximages.utils

import com.example.foximages.models.RawData
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitService {
    @GET("search")
    suspend fun getData(
        @Query("key") key: String = "LIVDSRZULELA",
        @Query("limit") limit: Int = 10,
        @Query("q") query: String = "",
        @Query("pos") next: Int = 1
    ): RawData
}