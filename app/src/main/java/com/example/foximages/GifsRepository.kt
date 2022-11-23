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
    constructor(database: AppDatabase) : this(
        gifsSource = GifsSource(),
        gifsStore = GifStore(database)
    )

    suspend fun allGifs(): Flow<PagingData<DataFromAPI>> = gifsStore.ensureIsNotEmpty().all()

    private suspend fun initData() {
        gifsStore.clear()
        for (page in 0..3) {
            val gifs = gifsSource.load(page)
            gifsStore.save(gifs)
        }
    }

    suspend fun getGifsByName(name: String) {
        gifsStore.clear()
        for (page in 0..3) {
            val gifs = gifsSource.load(page, name)
            gifsStore.save(gifs)
        }
    }

    private suspend fun GifStore.ensureIsNotEmpty() = apply {
        if (isEmpty()) {
            initData()
        }
    }
}