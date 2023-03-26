package com.buggily.enemy.data.theme.di.source.dynamic

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.buggily.enemy.data.theme.source.dynamic.DynamicLocalSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DynamicLocalSourceProvider {

    @Provides
    fun provides(
        dataStore: DataStore<Preferences>,
    ): DynamicLocalSource = DynamicLocalSource(
        dataStore = dataStore,
    )
}
