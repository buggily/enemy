package com.buggily.enemy.local.preferences.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.buggily.enemy.local.preferences.dynamic.LocalDynamicSource
import com.buggily.enemy.local.preferences.dynamic.LocalDynamicSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object LocalDynamicSourceableProvider {

    @Provides
    fun provides(
        preferencesDataStore: DataStore<Preferences>,
    ): LocalDynamicSourceable = LocalDynamicSource(
        preferencesDataStore = preferencesDataStore,
    )
}
