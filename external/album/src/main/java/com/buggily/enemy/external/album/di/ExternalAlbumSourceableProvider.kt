package com.buggily.enemy.external.album.di

import androidx.paging.PagingConfig
import com.buggily.enemy.external.album.ExternalAlbumQuerySource
import com.buggily.enemy.external.album.ExternalAlbumSource
import com.buggily.enemy.external.album.ExternalAlbumSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object ExternalAlbumSourceableProvider {

    @Provides
    fun provides(
        config: PagingConfig,
        externalAlbumQuerySource: ExternalAlbumQuerySource,
    ): ExternalAlbumSourceable = ExternalAlbumSource(
        config = config,
        externalAlbumQuerySource = externalAlbumQuerySource,
    )
}
