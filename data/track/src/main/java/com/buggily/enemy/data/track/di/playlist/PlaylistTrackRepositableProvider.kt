package com.buggily.enemy.data.track.di.playlist

import com.buggily.enemy.core.domain.GetDurationWithMetadata
import com.buggily.enemy.data.track.playlist.PlaylistTrackRepositable
import com.buggily.enemy.data.track.playlist.PlaylistTrackRepository
import com.buggily.enemy.external.track.ExternalTrackSourceable
import com.buggily.enemy.local.playlist.track.LocalPlaylistTrackSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object PlaylistTrackRepositableProvider {

    @Provides
    fun provides(
        localPlaylistTrackSource: LocalPlaylistTrackSourceable,
        externalTrackSource: ExternalTrackSourceable,
        getDurationWithMetadata: GetDurationWithMetadata,
    ): PlaylistTrackRepositable = PlaylistTrackRepository(
        localPlaylistTrackSource = localPlaylistTrackSource,
        externalTrackSource = externalTrackSource,
        getDurationWithMetadata = getDurationWithMetadata,
    )
}
