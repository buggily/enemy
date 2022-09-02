package com.buggily.enemy.data.di.repository.search

import com.buggily.enemy.data.repository.search.SearchRepository
import com.buggily.enemy.data.source.search.SearchSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SearchRepositoryProvider {

    @Provides
    fun provides(
        source: SearchSourceable,
    ): SearchRepository = SearchRepository(
        source = source,
    )
}
