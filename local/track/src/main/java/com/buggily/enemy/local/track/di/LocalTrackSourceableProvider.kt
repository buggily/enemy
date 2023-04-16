package com.buggily.enemy.local.track.di

import androidx.paging.PagingConfig
import com.buggily.enemy.local.track.LocalTrackDao
import com.buggily.enemy.local.track.LocalTrackSource
import com.buggily.enemy.local.track.LocalTrackSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object LocalTrackSourceableProvider {

    @Provides
    fun provides(
        config: PagingConfig,
        localTrackDao: LocalTrackDao,
    ): LocalTrackSourceable = LocalTrackSource(
        config = config,
        localTrackDao = localTrackDao,
    )
}
