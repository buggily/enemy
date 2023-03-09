package com.buggily.enemy.core.local.di.track

import com.buggily.enemy.core.local.EnemyDatabaseable
import com.buggily.enemy.local.track.TrackDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TrackDaoProvider {

    @Provides
    fun provides(
        enemyDatabase: EnemyDatabaseable,
    ): TrackDao = enemyDatabase.getTrackDao()
}
