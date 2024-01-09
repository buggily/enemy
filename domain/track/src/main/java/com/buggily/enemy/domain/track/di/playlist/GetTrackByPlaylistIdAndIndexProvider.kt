package com.buggily.enemy.domain.track.di.playlist

import com.buggily.enemy.data.track.playlist.PlaylistTrackRepositable
import com.buggily.enemy.domain.track.playlist.GetTrackByPlaylistIdAndIndex
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetTrackByPlaylistIdAndIndexProvider {

    @Provides
    fun provides(
        playlistTrackRepository: PlaylistTrackRepositable,
    ): GetTrackByPlaylistIdAndIndex = GetTrackByPlaylistIdAndIndex(
        playlistTrackRepository = playlistTrackRepository,
    )
}
