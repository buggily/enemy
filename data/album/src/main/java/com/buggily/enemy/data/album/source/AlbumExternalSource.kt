package com.buggily.enemy.data.album.source

import android.provider.MediaStore
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.buggily.enemy.core.query.Query
import com.buggily.enemy.external.album.Album
import com.buggily.enemy.external.album.AlbumPagingSource
import com.buggily.enemy.external.album.AlbumQuerySource
import kotlinx.coroutines.flow.Flow

class AlbumExternalSource(
    private val config: PagingConfig,
    private val source: AlbumQuerySource,
) : AlbumExternalSourceable {

    override fun getPaging(
        search: String,
    ): Flow<PagingData<Album>> = Pager(
        config = config,
        pagingSourceFactory = {
            AlbumPagingSource(
                config = config,
                source = source,
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
    ): Album = Query(
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
    ).let { checkNotNull(source.loadFirstOrNull(it)) }
}
