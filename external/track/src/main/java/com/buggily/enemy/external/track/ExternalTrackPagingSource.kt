package com.buggily.enemy.external.track

import androidx.paging.PagingConfig
import com.buggily.enemy.core.paging.QueryPagingSource
import com.buggily.enemy.core.query.Query

internal class ExternalTrackPagingSource(
    override val query: Query,
    override val config: PagingConfig,
    override val source: ExternalTrackQuerySource,
) : QueryPagingSource<ExternalTrack, ExternalTrackQuerySource>(
    query = query,
    config = config,
    source = source,
)
