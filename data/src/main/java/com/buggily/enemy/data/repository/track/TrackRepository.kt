package com.buggily.enemy.data.repository.track

import androidx.paging.PagingData
import com.buggily.enemy.data.Track
import com.buggily.enemy.data.source.track.TrackSourceable
import kotlinx.coroutines.flow.Flow

class TrackRepository(
    private val source: TrackSourceable,
) : TrackRepositable {
    override fun getByAlbumId(albumId: Long?): List<Track> = source.getByAlbumId(albumId)
    override fun getPagingByAlbumId(albumId: Long?): Flow<PagingData<Track>> = source.getPagingByAlbumId(albumId)
}