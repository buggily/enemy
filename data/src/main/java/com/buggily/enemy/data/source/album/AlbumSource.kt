package com.buggily.enemy.data.source.album

import android.provider.MediaStore
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.buggily.enemy.data.Album
import com.buggily.enemy.data.paging.AlbumPagingSource
import com.buggily.enemy.data.query.Query
import com.buggily.enemy.data.query.album.AlbumQuerySource
import kotlinx.coroutines.flow.Flow

class AlbumSource(
    private val config: PagingConfig,
    private val source: AlbumQuerySource,
) : AlbumSourceable {

    override fun getPaging(search: String): Flow<PagingData<Album>> {
        val source = AlbumPagingSource(
            config = config,
            source = source,
            query = Query(
                selections = Query.Selections(
                    Query.Selection.Expression.Like(
                        argIdentity = MediaStore.Audio.Albums.ALBUM,
                        expressionIdentity = "%$search%",
                    ),
                ),
                sort = Query.Sort(
                    columns = mapOf(MediaStore.Audio.Albums._ID to Query.Sort.Type.Number),
                    direction = Query.Sort.Direction.Ascending,
                ),
            ),
        )

        return Pager(
            config = config,
            pagingSourceFactory = { source },
        ).flow
    }

    override fun getByAlbumId(albumId: Long?): Album? {
        val query = Query(
            selections = Query.Selections(
                Query.Selection.Expression.Equals(
                    argIdentity = MediaStore.Audio.Albums._ID,
                    expressionIdentity = albumId,
                ),
            ),
            sort = Query.Sort(
                columns = mapOf(MediaStore.Audio.Albums._ID to Query.Sort.Type.Number),
                direction = Query.Sort.Direction.Ascending
            ),
        )

        return source.load(query).firstOrNull()
    }
}
