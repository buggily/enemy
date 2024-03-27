package com.buggily.enemy.domain.track.di.playlist

import com.buggily.enemy.data.track.playlist.PlaylistTrackRepositable
import com.buggily.enemy.domain.track.playlist.DeleteTracksByPlaylistId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DeleteTracksByPlaylistIdProvider {

    @Provides
    fun provides(
        trackRepository: PlaylistTrackRepositable,
    ): DeleteTracksByPlaylistId = DeleteTracksByPlaylistId(
        playlistTrackRepository = trackRepository,
    )
}
