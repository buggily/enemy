package com.buggily.enemy.data.playlist.di

import com.buggily.enemy.core.domain.GetInstantWithMetadataFromInstant
import com.buggily.enemy.data.playlist.PlaylistRepositable
import com.buggily.enemy.data.playlist.PlaylistRepository
import com.buggily.enemy.local.playlist.LocalPlaylistSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object PlaylistRepositableProvider {

    @Provides
    fun provides(
        localPlaylistSource: LocalPlaylistSourceable,
        getInstantWithMetadataFromInstant: GetInstantWithMetadataFromInstant,
    ): PlaylistRepositable = PlaylistRepository(
        localPlaylistSource = localPlaylistSource,
        getInstantWithMetadataFromInstant = getInstantWithMetadataFromInstant,
    )
}
