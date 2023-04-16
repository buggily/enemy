package com.buggily.enemy.data.playlist.di

import com.buggily.core.domain.GetInstantWithMetadataFromInstant
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
        getInstantWithMetadataFromInstant: GetInstantWithMetadataFromInstant,
        localPlaylistSource: LocalPlaylistSourceable,
    ): PlaylistRepositable = PlaylistRepository(
        getInstantWithMetadataFromInstant = getInstantWithMetadataFromInstant,
        localPlaylistSource = localPlaylistSource,
    )
}
