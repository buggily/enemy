package com.buggily.enemy.domain.use.search

import com.buggily.enemy.data.repository.search.SearchRepositable
import com.buggily.enemy.domain.map.search.SearchMapper
import com.buggily.enemy.domain.search.Search
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSearch(
    private val repository: SearchRepositable,
    private val mapper: SearchMapper,
) {

    operator fun invoke(): Flow<Search> = repository.get().map {
        mapper.mapTo(it)
    }
}
