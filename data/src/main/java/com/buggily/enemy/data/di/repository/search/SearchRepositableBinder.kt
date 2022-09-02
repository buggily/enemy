package com.buggily.enemy.data.di.repository.search

import com.buggily.enemy.data.repository.search.SearchRepositable
import com.buggily.enemy.data.repository.search.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SearchRepositableBinder {

    @Binds
    fun binds(
        repository: SearchRepository,
    ): SearchRepositable
}
