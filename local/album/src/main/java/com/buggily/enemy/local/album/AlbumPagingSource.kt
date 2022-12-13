package com.buggily.enemy.local.album

import androidx.paging.PagingConfig
import com.buggily.enemy.core.paging.QueryPagingSource
import com.buggily.enemy.core.query.Query

class AlbumPagingSource(
    override val config: PagingConfig,
    override val query: Query,
    override val source: AlbumQuerySource,
) : QueryPagingSource<Album, AlbumQuerySource>(
    config = config,
    query = query,
    source = source,
)
