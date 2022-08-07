package com.buggily.enemy.data.source.track

import androidx.paging.PagingData
import com.buggily.enemy.data.Track
import kotlinx.coroutines.flow.Flow

interface TrackSourceable {
    fun getByAlbumId(albumId: Long?): List<Track>
    fun getPagingByAlbumId(albumId: Long?): Flow<PagingData<Track>>
}
