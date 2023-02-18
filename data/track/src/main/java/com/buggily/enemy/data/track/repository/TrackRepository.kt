package com.buggily.enemy.data.track.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.core.domain.GetRuntime
import com.buggily.enemy.core.model.track.Track
import com.buggily.enemy.data.track.ext.map
import com.buggily.enemy.data.track.source.TrackSourceable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrackRepository(
    private val source: TrackSourceable,
    private val getRuntime: GetRuntime,
) : TrackRepositable {

    override fun getPaging(
        search: String,
    ): Flow<PagingData<Track>>  = source.getPaging(
        search = search,
    ).map {
        it.map { track -> track.map(getRuntime) }
    }

    override fun getByAlbumId(
        albumId: Long,
    ): List<Track> = source.getByAlbumId(albumId).map {
        it.map(getRuntime)
    }

    override fun getPagingByAlbumId(
        albumId: Long,
    ): Flow<PagingData<Track>> = source.getPagingByAlbumId(albumId).map {
        it.map { track -> track.map(getRuntime) }
    }
}
