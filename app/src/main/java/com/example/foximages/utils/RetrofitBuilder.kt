package com.example.foximages.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitBuilder {

    private const val BASE_URL = "https://g.tenor.com/v1/"

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: RetrofitService = getRetrofit().create(RetrofitService::class.java)
}