package com.buggily.enemy.data.repository.search

import com.buggily.enemy.data.Search
import com.buggily.enemy.data.source.search.SearchSourceable
import kotlinx.coroutines.flow.Flow

class SearchRepository(
    private val source: SearchSourceable,
) : SearchRepositable {

    override fun get(): Flow<Search> = source.get()
    override suspend fun set(search: Search) = source.set(search)
}
