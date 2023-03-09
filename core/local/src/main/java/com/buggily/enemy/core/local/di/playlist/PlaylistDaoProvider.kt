package com.buggily.enemy.core.local.di.playlist

import com.buggily.enemy.core.local.EnemyDatabaseable
import com.buggily.enemy.local.playlist.PlaylistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PlaylistDaoProvider {

    @Provides
    fun provides(
        database: EnemyDatabaseable,
    ): PlaylistDao = database.getPlaylistDao()
}
