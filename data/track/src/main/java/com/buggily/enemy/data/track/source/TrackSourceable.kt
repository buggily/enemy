package com.buggily.enemy.data.track.source

import androidx.paging.PagingData
import com.buggily.enemy.local.track.Track
import kotlinx.coroutines.flow.Flow

interface TrackSourceable {

    fun getPaging(search: String): Flow<PagingData<Track>>

    fun getByAlbumId(albumId: Long): List<Track>
    fun getPagingByAlbumId(albumId: Long): Flow<PagingData<Track>>
}
