package com.example.foximages.remote

import com.example.foximages.pojo.RawData
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitService {
    @GET("search")
    suspend fun getData(
        @Query("key") key: String = "LIVDSRZULELA",
        @Query("limit") limit: Int = 10,
        @Query("q") query: String = "excited",
        @Query("pos") next: Int = 1
    ): RawData
}