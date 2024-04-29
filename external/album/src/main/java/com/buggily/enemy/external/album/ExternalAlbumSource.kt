package com.buggily.enemy.external.album

import android.provider.MediaStore
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.buggily.enemy.core.query.Query
import kotlinx.coroutines.flow.Flow

internal class ExternalAlbumSource(
    private val config: PagingConfig,
    private val externalAlbumQuerySource: ExternalAlbumQuerySource,
) : ExternalAlbumSourceable {

    override fun getPaging(
        search: String,
    ): Flow<PagingData<ExternalAlbum>> = Pager(
        config = config,
        pagingSourceFactory = {
            ExternalAlbumPagingSource(
                config = config,
                source = externalAlbumQuerySource,
                query = Query(
                    selections = Query.Selections(
                        Query.Selection.Expression.Like(
                            argumentIdentity = MediaStore.Audio.Albums.ALBUM,
                            expressionIdentity = "%$search%",
                        ),
                    ),
                    sort = Query.Sort(
                        columns = mapOf(MediaStore.Audio.Albums._ID to Query.Sort.Type.Number),
                        direction = Query.Sort.Direction.Ascending,
                    ),
                ),
            )
        }
    ).flow

    override suspend fun getById(
        id: Long,
    ): ExternalAlbum? = Query(
        selections = Query.Selections(
            Query.Selection.Expression.Equals(
                argumentIdentity = MediaStore.Audio.Albums._ID,
                expressionIdentity = id,
            ),
        ),
        sort = Query.Sort(
            columns = mapOf(MediaStore.Audio.Albums._ID to Query.Sort.Type.Number),
            direction = Query.Sort.Direction.Ascending
        ),
    ).let { externalAlbumQuerySource.loadFirstOrNull(it) }
}
