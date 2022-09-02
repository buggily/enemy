package com.buggily.enemy.data.paging

import androidx.paging.PagingConfig
import com.buggily.enemy.data.track.Track
import com.buggily.enemy.data.query.Query
import com.buggily.enemy.data.query.track.TrackQuerySource

class TrackPagingSource(
    override val config: PagingConfig,
    override val query: Query,
    override val source: TrackQuerySource,
) : QueryPagingSource<Track, TrackQuerySource>(
    config = config,
    query = query,
    source = source,
)
