package com.buggily.enemy.external.track

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface ExternalTrackSourceable {

    fun getPaging(search: String): Flow<PagingData<ExternalTrack>>
    fun getPagingByAlbumId(albumId: Long): Flow<PagingData<ExternalTrack>>

    suspend fun getById(id: Long): ExternalTrack
    suspend fun getByAlbumId(albumId: Long): List<ExternalTrack>
}
