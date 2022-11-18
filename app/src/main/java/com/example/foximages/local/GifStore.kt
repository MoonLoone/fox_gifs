package com.example.foximages.local

import androidx.paging.*
import com.example.foximages.pojo.DataFromAPI
import com.example.foximages.pojo.Gifs
import com.example.foximages.pojo.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GifStore(database: AppDatabase) {

    private val gifs = database.gifs

    fun all(): Flow<PagingData<DataFromAPI>> = pagingGif { gifs.getAll() }

    suspend fun save(gifs: List<DataFromAPI>) {
        this.gifs.insertAll(gifs.map { it.toLocalEntity() })
    }

    suspend fun isEmpty(): Boolean = gifs.count() == 0L

    private fun DataFromAPI.toLocalEntity() = DatabaseEntity(
        id_from_api = id,
        contentDescription = contentDescription,
        url = media?.get(0)?.gif?.url,
        width = media?.get(0)?.gif?.dims?.get(0),
        height = media?.get(0)?.gif?.dims?.get(1),
    )

    private fun DatabaseEntity.fromLocalEntity() = DataFromAPI(
        id = id_from_api,
        contentDescription = contentDescription,
        media = listOf(
            Media(
                Gifs(
                    url = url,
                    dims = listOf(width ?: 0, height ?: 0)
                )
            )
        )
    )

    private fun pagingGif(
        block: () -> PagingSource<Int, DatabaseEntity>,
    ): Flow<PagingData<DataFromAPI>> = Pager(PagingConfig(pageSize = 20)) { block() }.flow.map {
        page -> page.map { it.fromLocalEntity() }
    }
}
