package com.example.foximages.local

import androidx.paging.*
import com.example.foximages.utils.Mapper
import com.example.foximages.pojo.DataFromApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GifStore(private val gifsDao: DatabaseDao) {

    fun all(): Flow<PagingData<DataFromApi>> = pagingGif { gifsDao.getAll() }

    suspend fun save(gifs: List<DataFromApi>) =
        gifsDao.insert(gifs.map { Mapper.toLocalEntity(it) })

    suspend fun clear() = gifsDao.clear()

    suspend fun isEmpty(): Boolean = gifsDao.count() == 0L

    private fun pagingGif(
        block: () -> PagingSource<Int, DatabaseEntity>,
    ): Flow<PagingData<DataFromApi>> =
        Pager(PagingConfig(pageSize = 20)) { block() }.flow.map { page ->
            page.map { Mapper.fromLocalEntity(it) }
        }

}
