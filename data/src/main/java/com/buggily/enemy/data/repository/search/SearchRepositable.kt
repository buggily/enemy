package com.buggily.enemy.data.repository.search

import com.buggily.enemy.data.Search
import kotlinx.coroutines.flow.Flow

interface SearchRepositable {
    fun get(): Flow<Search>
    suspend fun set(search: Search)
}
