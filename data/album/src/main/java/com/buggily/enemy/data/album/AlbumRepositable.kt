package com.buggily.enemy.data.album

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface AlbumRepositable {

    fun getPaging(
        search: String,
    ): Flow<PagingData<Album>>


    suspend fun getById(
        id: Long,
    ): Album?
}
