package com.buggily.enemy.external.album

import androidx.paging.PagingConfig
import com.buggily.enemy.core.paging.QueryPagingSource
import com.buggily.enemy.core.query.Query

internal class ExternalAlbumPagingSource(
    override val query: Query,
    override val config: PagingConfig,
    override val source: ExternalAlbumQuerySource,
) : QueryPagingSource<ExternalAlbum, ExternalAlbumQuerySource>(
    query = query,
    config = config,
    source = source,
)
