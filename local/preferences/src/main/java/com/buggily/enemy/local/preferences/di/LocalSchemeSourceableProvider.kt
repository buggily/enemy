package com.buggily.enemy.local.preferences.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.buggily.enemy.local.preferences.scheme.LocalSchemeSource
import com.buggily.enemy.local.preferences.scheme.LocalSchemeSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object LocalSchemeSourceableProvider {

    @Provides
    fun provides(
        preferencesDataStore: DataStore<Preferences>,
    ): LocalSchemeSourceable = LocalSchemeSource(
        preferencesDataStore = preferencesDataStore,
    )
}
