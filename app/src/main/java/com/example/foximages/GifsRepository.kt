package com.example.foximages

import androidx.paging.PagingData
import com.example.foximages.local.AppDatabase
import com.example.foximages.local.GifStore
import com.example.foximages.pojo.DataFromApi
import com.example.foximages.remote.GifsSource
import kotlinx.coroutines.flow.Flow

class GifsRepository(
    database: AppDatabase,
    private val gifsSource: GifsSource = GifsSource(),
    private val gifsStore: GifStore = GifStore(database.gifs)
) {

    suspend fun gifs(): Flow<PagingData<DataFromApi>> = gifsStore.ensureIsNotEmpty().all()

    suspend fun loadGifs(name: String = "enabled") {
        gifsStore.clear()
        repeat(3) { page ->
            val gifs = gifsSource.load(page, name)
            gifsStore.save(gifs)
        }
    }

    private suspend fun GifStore.ensureIsNotEmpty() = apply {
        if (isEmpty()) {
            loadGifs()
        }
    }
}