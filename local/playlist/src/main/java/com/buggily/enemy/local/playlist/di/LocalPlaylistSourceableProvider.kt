package com.buggily.enemy.local.playlist.di

import androidx.paging.PagingConfig
import com.buggily.enemy.local.playlist.LocalPlaylistDao
import com.buggily.enemy.local.playlist.LocalPlaylistSource
import com.buggily.enemy.local.playlist.LocalPlaylistSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object LocalPlaylistSourceableProvider {

    @Provides
    fun provides(
        config: PagingConfig,
        localPlaylistDao: LocalPlaylistDao,
    ): LocalPlaylistSourceable = LocalPlaylistSource(
        config = config,
        localPlaylistDao = localPlaylistDao,
    )
}
