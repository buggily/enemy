package com.buggily.enemy.domain.track.di

import com.buggily.enemy.data.track.TrackRepositable
import com.buggily.enemy.domain.track.GetTrackByPlaylistIdAndIndex
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetTrackByPlaylistIdAndIndexProvider {

    @Provides
    fun provides(
        repository: TrackRepositable,
    ): GetTrackByPlaylistIdAndIndex = GetTrackByPlaylistIdAndIndex(
        repository = repository,
    )
}
