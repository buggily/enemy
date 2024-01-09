package com.buggily.enemy.core.local.di.preferences

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
internal object PreferencesDataStoreProvider {

    @Provides
    fun provides(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.preferencesDataStore

    private const val name = "preferences"
    private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        name
    )
}
