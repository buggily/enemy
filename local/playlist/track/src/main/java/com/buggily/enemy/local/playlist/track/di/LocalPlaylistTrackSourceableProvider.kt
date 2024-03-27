package com.buggily.enemy.local.playlist.track.di

import androidx.paging.PagingConfig
import com.buggily.enemy.local.playlist.track.LocalPlaylistTrackDao
import com.buggily.enemy.local.playlist.track.LocalPlaylistTrackSource
import com.buggily.enemy.local.playlist.track.LocalPlaylistTrackSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object LocalPlaylistTrackSourceableProvider {

    @Provides
    fun provides(
        config: PagingConfig,
        localPlaylistTrackDao: LocalPlaylistTrackDao,
    ): LocalPlaylistTrackSourceable = LocalPlaylistTrackSource(
        config = config,
        localPlaylistTrackDao = localPlaylistTrackDao,
    )
}
