package com.buggily.enemy.data.di.source.theme.dynamic

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.buggily.enemy.data.source.theme.dynamic.DynamicSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DynamicSourceModule {

    @Provides
    fun provides(
        preferencesDataStore: DataStore<Preferences>,
    ): DynamicSource = DynamicSource(
        preferencesDataStore = preferencesDataStore,
    )
}
