package com.buggily.enemy.domain.di.map.search

import com.buggily.enemy.domain.map.search.SearchMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SearchMapperProvider {

    @Provides
    fun provides(): SearchMapper = SearchMapper()
}
