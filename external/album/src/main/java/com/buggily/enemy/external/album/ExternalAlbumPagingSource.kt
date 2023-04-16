package com.buggily.enemy.external.album

import androidx.paging.PagingConfig
import com.buggily.enemy.core.paging.QueryPagingSource
import com.buggily.enemy.core.query.Query

internal class ExternalAlbumPagingSource(
    override val config: PagingConfig,
    override val query: Query,
    override val source: ExternalAlbumQuerySource,
) : QueryPagingSource<ExternalAlbum, ExternalAlbumQuerySource>(
    config = config,
    query = query,
    source = source,
)
