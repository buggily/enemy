package com.buggily.enemy.domain.track.di

import com.buggily.enemy.data.track.TrackRepositable
import com.buggily.enemy.domain.track.GetTrackPagingByPopularity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object GetTrackPagingByPopularityProvider {

    @Provides
    fun provides(
        trackRepository: TrackRepositable,
    ): GetTrackPagingByPopularity = GetTrackPagingByPopularity(
        trackRepository = trackRepository,
    )
}
