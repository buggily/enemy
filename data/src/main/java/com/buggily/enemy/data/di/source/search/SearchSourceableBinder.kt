package com.buggily.enemy.data.di.source.search

import com.buggily.enemy.data.source.search.SearchSource
import com.buggily.enemy.data.source.search.SearchSourceable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SearchSourceableBinder {

    @Binds
    fun binds(
        source: SearchSource,
    ): SearchSourceable
}
