package com.buggily.enemy.data.paging

import androidx.paging.PagingConfig
import com.buggily.enemy.data.Album
import com.buggily.enemy.data.query.Query
import com.buggily.enemy.data.query.album.AlbumQuerySource

class AlbumPagingSource(
    override val config: PagingConfig,
    override val query: Query,
    override val source: AlbumQuerySource,
) : QueryPagingSource<Album, AlbumQuerySource>(
    config = config,
    query = query,
    source = source,
)
