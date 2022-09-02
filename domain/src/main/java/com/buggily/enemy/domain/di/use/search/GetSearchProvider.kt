package com.buggily.enemy.domain.di.use.search

import com.buggily.enemy.data.repository.search.SearchRepositable
import com.buggily.enemy.domain.map.search.SearchMapper
import com.buggily.enemy.domain.use.search.GetSearch
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetSearchProvider {

    @Provides
    fun provides(
        repository: SearchRepositable,
        mapper: SearchMapper,
    ): GetSearch = GetSearch(
        repository = repository,
        mapper = mapper,
    )
}
