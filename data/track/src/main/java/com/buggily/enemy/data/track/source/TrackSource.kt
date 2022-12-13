package com.buggily.enemy.data.track.source

import android.provider.MediaStore
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.buggily.enemy.core.query.Query
import com.buggily.enemy.local.track.Track
import com.buggily.enemy.local.track.TrackPagingSource
import com.buggily.enemy.local.track.TrackQuerySource
import kotlinx.coroutines.flow.Flow

class TrackSource(
    private val config: PagingConfig,
    private val source: TrackQuerySource,
) : TrackSourceable {

    override fun getByAlbumId(albumId: Long): List<Track> = Query(
        selections = Query.Selections(
            Query.Selection.Expression.Equals(
                argIdentity = MediaStore.Audio.Media.ALBUM_ID,
                expressionIdentity = albumId,
            ),
            Query.Selection.Operator.And,
            Query.Selection.Expression.Unequals(
                argIdentity = MediaStore.Audio.Media.IS_MUSIC,
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

    override fun getPagingByAlbumId(albumId: Long): Flow<PagingData<Track>> {
        val source = TrackPagingSource(
            config = config,
            source = source,
            query = Query(
                selections = Query.Selections(
                    Query.Selection.Expression.Equals(
                        argIdentity = MediaStore.Audio.Media.ALBUM_ID,
                        expressionIdentity = albumId,
                    ),
                    Query.Selection.Operator.And,
                    Query.Selection.Expression.Unequals(
                        argIdentity = MediaStore.Audio.Media.IS_MUSIC,
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

        return Pager(
            config = config,
            pagingSourceFactory = { source },
        ).flow
    }
}
