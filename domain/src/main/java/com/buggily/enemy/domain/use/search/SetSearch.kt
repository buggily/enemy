package com.buggily.enemy.domain.use.search

import com.buggily.enemy.data.repository.search.SearchRepositable
import com.buggily.enemy.domain.map.search.SearchMapper
import com.buggily.enemy.domain.search.Search

class SetSearch(
    private val repository: SearchRepositable,
    private val mapper: SearchMapper,
) {

    suspend operator fun invoke(search: Search) = mapper.mapFrom(search).let {
        repository.set(it)
    }
}
