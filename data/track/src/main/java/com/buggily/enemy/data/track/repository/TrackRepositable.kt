package com.buggily.enemy.data.track.repository

import androidx.paging.PagingData
import com.buggily.enemy.core.model.track.Track
import kotlinx.coroutines.flow.Flow

interface TrackRepositable {
    fun getByAlbumId(albumId: Long): List<Track>
    fun getPagingByAlbumId(albumId: Long): Flow<PagingData<Track>>
}
