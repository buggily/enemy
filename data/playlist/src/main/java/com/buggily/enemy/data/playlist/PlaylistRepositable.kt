package com.buggily.enemy.data.playlist

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface PlaylistRepositable {

    fun getPaging(
        search: String,
    ): Flow<PagingData<Playlist>>

    suspend fun getById(
        id: Long,
    ): Playlist?

    suspend fun deleteById(
        id: Long,
    )

    suspend fun updateById(
        id: Long,
    )

    suspend fun updateById(
        id: Long,
        name: String,
    )

    suspend fun create(
        name: String,
    )
}
