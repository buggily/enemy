package com.buggily.enemy.data.album.di.source

import androidx.paging.PagingConfig
import com.buggily.enemy.data.album.source.AlbumSource
import com.buggily.enemy.local.album.AlbumQuerySource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AlbumSourceProvider {

    @Provides
    fun provides(
        config: PagingConfig,
        source: AlbumQuerySource,
    ): AlbumSource = AlbumSource(
        config = config,
        source = source,
    )
}
