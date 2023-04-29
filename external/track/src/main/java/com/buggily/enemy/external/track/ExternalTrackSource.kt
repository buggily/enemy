package com.buggily.enemy.external.track

import android.provider.MediaStore
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.buggily.enemy.core.query.Query
import kotlinx.coroutines.flow.Flow

internal class ExternalTrackSource(
    private val config: PagingConfig,
    private val externalTrackQuerySource: ExternalTrackQuerySource,
) : ExternalTrackSourceable {

    override fun getPaging(
        search: String,
    ): Flow<PagingData<ExternalTrack>> = Pager(
        config = config,
        pagingSourceFactory = {
            ExternalTrackPagingSource(
                config = config,
                source = externalTrackQuerySource,
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
    ): Flow<PagingData<ExternalTrack>> = Pager(
        config = config,
        pagingSourceFactory = {
            ExternalTrackPagingSource(
                config = config,
                source = externalTrackQuerySource,
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

    override suspend fun getById(
        id: Long,
    ): ExternalTrack? = Query(
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
    ).let { externalTrackQuerySource.loadFirstOrNull(it) }

    override suspend fun getByAlbumId(
        albumId: Long,
    ): List<ExternalTrack> = Query(
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
    ).let { externalTrackQuerySource.load(it) }
}
