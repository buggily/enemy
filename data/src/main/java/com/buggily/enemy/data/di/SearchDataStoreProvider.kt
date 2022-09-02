package com.buggily.enemy.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.buggily.enemy.data.Search
import com.buggily.enemy.data.search.SearchSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SearchDataStoreProvider {

    @Provides
    fun provides(
        @ApplicationContext context: Context,
    ): DataStore<Search> = context.searchDataStore

    private const val IDENTITY = "search.pb"
    private val Context.searchDataStore: DataStore<Search> by dataStore(
        fileName = IDENTITY,
        serializer = SearchSerializer,
    )
}
