package com.buggily.enemy.data.di.source.album

import androidx.paging.PagingConfig
import com.buggily.enemy.data.query.album.AlbumQuerySource
import com.buggily.enemy.data.source.album.AlbumSource
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
