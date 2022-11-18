package com.example.foximages.remote

import com.example.foximages.pojo.DataFromAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GifsSource {
    suspend fun load(): List<DataFromAPI> = withContext(Dispatchers.IO){
        RetrofitBuilder.apiService.getData().results?: listOf()
    }
}