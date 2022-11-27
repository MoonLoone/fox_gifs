package com.example.foximages.utils

import com.example.foximages.local.DatabaseEntity
import com.example.foximages.pojo.DataFromApi
import com.example.foximages.pojo.Gifs
import com.example.foximages.pojo.Media

object Mapper {
    fun toLocalEntity(dataFromApi: DataFromApi) = DatabaseEntity(
        apiId = dataFromApi.id,
        contentDescription = dataFromApi.contentDescription,
        url = dataFromApi.media?.get(0)?.gif?.url,
        width = dataFromApi.media?.get(0)?.gif?.dims?.get(0),
        height = dataFromApi.media?.get(0)?.gif?.dims?.get(1),
    )

    fun fromLocalEntity(databaseEntity: DatabaseEntity) = DataFromApi(
        id = databaseEntity.apiId,
        contentDescription = databaseEntity.contentDescription,
        media = listOf(
            Media(
                Gifs(
                    url = databaseEntity.url,
                    dims = listOf(databaseEntity.width ?: 0, databaseEntity.height ?: 0)
                )
            )
        )
    )
}