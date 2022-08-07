package com.buggily.enemy.data.di.source.theme

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.buggily.enemy.data.source.theme.ThemeSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ThemeSourceProvider {

    @Provides
    fun provides(
        preferencesDataStore: DataStore<Preferences>,
    ): ThemeSource = ThemeSource(
        preferencesDataStore = preferencesDataStore,
    )
}