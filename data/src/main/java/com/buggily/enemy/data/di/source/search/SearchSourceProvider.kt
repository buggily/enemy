package com.buggily.enemy.data.di.source.search

import androidx.datastore.core.DataStore
import com.buggily.enemy.data.Search
import com.buggily.enemy.data.source.search.SearchSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SearchSourceProvider {

    @Provides
    fun provides(
        dataStore: DataStore<Search>,
    ): SearchSource = SearchSource(
        dataStore = dataStore,
    )
}
