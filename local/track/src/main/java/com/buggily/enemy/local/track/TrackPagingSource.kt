package com.buggily.enemy.local.track

import androidx.paging.PagingConfig
import com.buggily.enemy.core.paging.QueryPagingSource
import com.buggily.enemy.core.query.Query

class TrackPagingSource(
    override val config: PagingConfig,
    override val query: Query,
    override val source: TrackQuerySource,
) : QueryPagingSource<Track, TrackQuerySource>(
    config = config,
    query = query,
    source = source,
)
