package com.buggily.enemy.data.track

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.enemy.core.domain.GetInstant
import com.buggily.enemy.external.track.ExternalTrack
import com.buggily.enemy.external.track.ExternalTrackSourceable
import com.buggily.enemy.local.track.LocalTrack
import com.buggily.enemy.local.track.LocalTrackSourceable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant

internal class TrackRepository(
    private val localTrackSource: LocalTrackSourceable,
    private val externalTrackSource: ExternalTrackSourceable,
    private val getInstant: GetInstant,
) : TrackRepositable {

    override fun getPaging(
        search: String,
    ): Flow<PagingData<Track>> = externalTrackSource.getPaging(
        search = search,
    ).map { pagingData: PagingData<ExternalTrack> ->
        pagingData.map { it.to() }
    }

    override fun getPagingByRecency(): Flow<PagingData<TrackWithMetadata>> =
        localTrackSource.getPagingByRecency().map { pagingData: PagingData<LocalTrack> ->
            pagingData.map {
                externalTrackSource.getById(it.id).toWithMetadata(
                    id = it.id,
                    plays = it.plays,
                    firstPlayInstant = it.firstPlayInstant,
                    lastPlayInstant = it.lastPlayInstant,
                )
            }
        }

    override fun getPagingByPopularity(): Flow<PagingData<TrackWithMetadata>> =
        localTrackSource.getPagingByPopularity().map { pagingData: PagingData<LocalTrack> ->
            pagingData.map {
                externalTrackSource.getById(it.id).toWithMetadata(
                    id = it.id,
                    plays = it.plays,
                    firstPlayInstant = it.firstPlayInstant,
                    lastPlayInstant = it.lastPlayInstant,
                )
            }
        }

    override fun getPagingByAlbumId(
        albumId: Long,
    ): Flow<PagingData<Track>> = externalTrackSource.getPagingByAlbumId(
        albumId = albumId,
    ).map { pagingData: PagingData<ExternalTrack> ->
        pagingData.map { it.to() }
    }

    override suspend fun getById(
        id: Long,
    ): Track? = externalTrackSource.getById(
        id = id,
    )?.to()

    override suspend fun getByAlbumId(
        albumId: Long,
    ): List<Track> = externalTrackSource.getByAlbumId(
        albumId = albumId,
    ).map { it.to() }

    override suspend fun incrementPlaysById(id: Long) {
        val track: LocalTrack? = localTrackSource.getById(id)

        if (track == null) {
            insertById(id)
            return
        }

        track.copy(
            plays = track.plays.inc(),
            lastPlayInstant = getInstant(),
        ).let { localTrackSource.update(it) }
    }

    private suspend fun insertById(id: Long) {
        val instant: Instant = getInstant()

        LocalTrack(
            id = id,
            plays = 1,
            firstPlayInstant = instant,
            lastPlayInstant = instant,
        ).let { localTrackSource.insert(it) }
    }
}
