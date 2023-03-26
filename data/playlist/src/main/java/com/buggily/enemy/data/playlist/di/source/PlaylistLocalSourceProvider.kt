package com.buggily.enemy.data.playlist.di.source

import androidx.paging.PagingConfig
import com.buggily.enemy.data.playlist.source.PlaylistLocalSource
import com.buggily.enemy.local.playlist.PlaylistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PlaylistLocalSourceProvider {

    @Provides
    fun provides(
        config: PagingConfig,
        dao: PlaylistDao,
    ): PlaylistLocalSource = PlaylistLocalSource(
        config = config,
        dao = dao,
    )
}
