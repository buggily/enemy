package com.buggily.enemy.core.local.di.playlist

import com.buggily.enemy.core.local.EnemyDatabaseable
import com.buggily.enemy.local.playlist.LocalPlaylistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object LocalPlaylistDaoProvider {

    @Provides
    fun provides(
        database: EnemyDatabaseable,
    ): LocalPlaylistDao = database.getLocalPlaylistDao()
}
