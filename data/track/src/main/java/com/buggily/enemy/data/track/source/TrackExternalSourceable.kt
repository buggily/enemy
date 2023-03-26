package com.buggily.enemy.data.track.source

import androidx.paging.PagingData
import com.buggily.enemy.external.track.Track
import kotlinx.coroutines.flow.Flow

interface TrackExternalSourceable {

    fun getPaging(search: String): Flow<PagingData<Track>>
    fun getPagingByAlbumId(albumId: Long): Flow<PagingData<Track>>

    suspend fun getById(id: Long): Track
    suspend fun getByAlbumId(albumId: Long): List<Track>
}
