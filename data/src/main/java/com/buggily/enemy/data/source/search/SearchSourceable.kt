package com.buggily.enemy.data.source.search

import com.buggily.enemy.data.Search
import kotlinx.coroutines.flow.Flow

interface SearchSourceable {
    fun get(): Flow<Search>
    suspend fun set(search: Search)
}
