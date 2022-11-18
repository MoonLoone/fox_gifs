package com.example.foximages

import androidx.paging.PagingData
import com.example.foximages.local.AppDatabase
import com.example.foximages.local.GifStore
import com.example.foximages.pojo.DataFromAPI
import com.example.foximages.remote.GifsSource
import kotlinx.coroutines.flow.Flow

class GifsRepository(
    private val gifsSource: GifsSource,
    private val gifsStore: GifStore
) {
    constructor(database: AppDatabase): this(
        gifsSource = GifsSource(),
        gifsStore = GifStore(database)
    )

    suspend fun allGifs(): Flow<PagingData<DataFromAPI>> = gifsStore.ensureIsNotEmpty().all()

    private suspend fun GifStore.ensureIsNotEmpty() = apply {
        if (isEmpty()){
            val gifs = gifsSource.load()
            save(gifs)
        }
    }
}