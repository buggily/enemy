package com.buggily.enemy.domain.track.di

import com.buggily.enemy.data.track.TrackRepositable
import com.buggily.enemy.domain.track.InsertTrackByPlaylistId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object InsertTrackByPlaylistIdProvider {

    @Provides
    fun provides(
        trackRepository: TrackRepositable,
    ): InsertTrackByPlaylistId = InsertTrackByPlaylistId(
        trackRepository = trackRepository,
    )
}
