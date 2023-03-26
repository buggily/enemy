package com.buggily.enemy.data.album.di.source

import androidx.paging.PagingConfig
import com.buggily.enemy.data.album.source.AlbumExternalSource
import com.buggily.enemy.external.album.AlbumQuerySource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AlbumExternalSourceProvider {

    @Provides
    fun provides(
        config: PagingConfig,
        source: AlbumQuerySource,
    ): AlbumExternalSource = AlbumExternalSource(
        config = config,
        source = source,
    )
}
