package com.buggily.enemy.data.theme.di.source.scheme

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.buggily.enemy.data.theme.source.scheme.SchemeSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SchemeSourceProvider {

    @Provides
    fun provides(
        dataStore: DataStore<Preferences>,
    ): SchemeSource = SchemeSource(
        dataStore = dataStore,
    )
}
