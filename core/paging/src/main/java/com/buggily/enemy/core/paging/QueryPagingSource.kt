package com.buggily.enemy.core.paging

import androidx.paging.PagingConfig
import com.buggily.enemy.core.query.Query
import com.buggily.enemy.core.query.QuerySource

abstract class QueryPagingSource<Value : Any, Source : QuerySource<Value>>(
    open val config: PagingConfig,
    open val query: Query,
    open val source: Source,
) : PositionalPagingSource<Value>() {

    override suspend fun load(
        params: LoadParams<Key>,
    ): LoadResult<Key, Value> {
        val key: Key = params.key ?: Key.getDefault(config)

        val data: List<Value> = query.apply {
            limit = Query.Limit(key.limit)
            offset = Query.Offset(key.offset)
        }.let { source.load(it) }

        return LoadResult.Page(
            data = data,
            prevKey = key.getPrev(),
            nextKey = key.getNext(data),
        )
    }
}
