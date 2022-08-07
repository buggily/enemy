package com.buggily.enemy.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PreferencesDataStoreProvider {

    @Provides
    fun provides(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.preferencesDataStore

    private const val IDENTITY = "preferences"
    private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(IDENTITY)
}
