package com.buggily.enemy.data.track.source

import android.provider.MediaStore
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.enemy.core.query.Query
import com.buggily.enemy.local.track.TrackDao
import com.buggily.enemy.local.track.TrackQuerySource
import com.buggily.enemy.local.track.paging.Track
import com.buggily.enemy.local.track.paging.TrackPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.buggily.enemy.local.track.Track as LocalTrack

class TrackSource(
    private val config: PagingConfig,
    private val source: TrackQuerySource,
    private val dao: TrackDao,
) : TrackSourceable {

    override fun getPaging(
        search: String,
    ): Flow<PagingData<Track>> = Pager(
        config = config,
        pagingSourceFactory = {
            TrackPagingSource(
                config = config,
                source = source,
                query = Query(
                    selections = Query.Selections(
                        Query.Selection.Expression.Like(
                            argumentIdentity = MediaStore.Audio.Media.TITLE,
                            expressionIdentity = "%$search%",
                        ),
                        Query.Selection.Operator.And,
                        Query.Selection.Expression.Unequals(
                            argumentIdentity = MediaStore.Audio.Media.IS_MUSIC,
                            expressionIdentity = 0,
                        ),
                    ),
                    sort = Query.Sort(
                        columns = mapOf(
                            MediaStore.Audio.Media.TITLE to Query.Sort.Type.Text,
                        ),
                        direction = Query.Sort.Direction.Ascending,
                    ),
                ),
            )
        },
    ).flow

    override fun getPagingByAlbumId(
        albumId: Long,
    ): Flow<PagingData<Track>> = Pager(
        config = config,
        pagingSourceFactory = {
            TrackPagingSource(
                config = config,
                source = source,
                query = Query(
                    selections = Query.Selections(
                        Query.Selection.Expression.Equals(
                            argumentIdentity = MediaStore.Audio.Media.ALBUM_ID,
                            expressionIdentity = albumId,
                        ),
                        Query.Selection.Operator.And,
                        Query.Selection.Expression.Unequals(
                            argumentIdentity = MediaStore.Audio.Media.IS_MUSIC,
                            expressionIdentity = 0,
                        ),
                    ),
                    sort = Query.Sort(
                        columns = mapOf(
                            MediaStore.Audio.Media.DISC_NUMBER to Query.Sort.Type.Number,
                            MediaStore.Audio.Media.CD_TRACK_NUMBER to Query.Sort.Type.Number,
                        ),
                        direction = Query.Sort.Direction.Ascending,
                    ),
                ),
            )
        },
    ).flow

    override fun getPagingByPlaylistId(
        playlistId: Long,
    ): Flow<PagingData<Track>> = Pager(
        config = config,
        pagingSourceFactory = {
            dao.getTrackPagingByPlaylistId(
                playlistId = playlistId,
            )
        },
    ).flow.map { pagingData: PagingData<LocalTrack> ->
        pagingData.map { track: LocalTrack ->
            Query(
                selections = Query.Selections(
                    Query.Selection.Expression.Equals(
                        argumentIdentity = MediaStore.Audio.Media._ID,
                        expressionIdentity = track.id,
                    ),
                    Query.Selection.Operator.And,
                    Query.Selection.Expression.Unequals(
                        argumentIdentity = MediaStore.Audio.Media.IS_MUSIC,
                        expressionIdentity = 0,
                    ),
                ),
                sort = Query.Sort.NONE,
            ).let { checkNotNull(source.loadFirstOrNull(it)) }
        }
    }

    override suspend fun getById(
        id: Long,
    ): Track = Query(
        selections = Query.Selections(
            Query.Selection.Expression.Equals(
                argumentIdentity = MediaStore.Audio.Media._ID,
                expressionIdentity = id,
            ),
            Query.Selection.Operator.And,
            Query.Selection.Expression.Unequals(
                argumentIdentity = MediaStore.Audio.Media.IS_MUSIC,
                expressionIdentity = 0,
            ),
        ),
        sort = Query.Sort.NONE,
    ).let { checkNotNull(source.loadFirstOrNull(it)) }

    override suspend fun getByAlbumId(
        albumId: Long,
    ): List<Track> = Query(
        selections = Query.Selections(
            Query.Selection.Expression.Equals(
                argumentIdentity = MediaStore.Audio.Media.ALBUM_ID,
                expressionIdentity = albumId,
            ),
            Query.Selection.Operator.And,
            Query.Selection.Expression.Unequals(
                argumentIdentity = MediaStore.Audio.Media.IS_MUSIC,
                expressionIdentity = 0,
            ),
        ),
        sort = Query.Sort(
            columns = mapOf(
                MediaStore.Audio.Media.DISC_NUMBER to Query.Sort.Type.Number,
                MediaStore.Audio.Media.CD_TRACK_NUMBER to Query.Sort.Type.Number,
            ),
            direction = Query.Sort.Direction.Ascending,
        ),
    ).let { source.load(it) }

    override suspend fun getByPlaylistId(
        playlistId: Long,
    ): List<Track> = dao.getTracksByPlaylistId(
        playlistId = playlistId,
    ).map { getById(it.id) }
}
