package com.buggily.enemy.core.local.di.track.playlist

import com.buggily.enemy.core.local.EnemyDatabaseable
import com.buggily.enemy.local.playlist.track.LocalPlaylistTrackDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object LocalPlaylistTrackDaoProvider {

    @Provides
    fun provides(
        enemyDatabase: EnemyDatabaseable,
    ): LocalPlaylistTrackDao = enemyDatabase.getLocalPlaylistTrackDao()
}
