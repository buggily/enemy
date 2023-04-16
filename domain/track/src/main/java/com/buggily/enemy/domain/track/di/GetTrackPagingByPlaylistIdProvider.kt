package com.buggily.enemy.domain.track.di

import com.buggily.enemy.data.track.TrackRepositable
import com.buggily.enemy.domain.track.GetTrackPagingByPlaylistId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object GetTrackPagingByPlaylistIdProvider {

    @Provides
    fun provides(
        repository: TrackRepositable,
    ): GetTrackPagingByPlaylistId = GetTrackPagingByPlaylistId(
        repository = repository,
    )
}
