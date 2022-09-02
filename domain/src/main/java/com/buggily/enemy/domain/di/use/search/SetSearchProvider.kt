package com.buggily.enemy.domain.di.use.search

import com.buggily.enemy.data.repository.search.SearchRepository
import com.buggily.enemy.domain.map.search.SearchMapper
import com.buggily.enemy.domain.use.search.SetSearch
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SetSearchProvider {

    @Provides
    fun provides(
        repository: SearchRepository,
        mapper: SearchMapper,
    ): SetSearch = SetSearch(
        repository = repository,
        mapper = mapper,
    )
}
