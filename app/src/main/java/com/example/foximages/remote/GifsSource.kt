package com.example.foximages.remote

import com.example.foximages.pojo.DataFromApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GifsSource {
    suspend fun load(pageNumber:Int, name:String = "excited"): List<DataFromApi> = withContext(Dispatchers.IO){
        RetrofitBuilder.apiService.getData(next = pageNumber, query = name).results
    }
}