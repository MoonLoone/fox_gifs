package com.example.foximages.remote

import com.example.foximages.pojo.RawData
import retrofit2.http.GET
import retrofit2.http.Query


const val KEY = "LIVDSRZULELA"

interface RetrofitService {
    @GET("search?key=$KEY")
    suspend fun getData(
        @Query("key") key: String = "LIVDSRZULELA",
        @Query("limit") limit: Int = 30,
        @Query("q") query: String = "excited",
        @Query("pos") next: Int = 1,
    ): RawData
}