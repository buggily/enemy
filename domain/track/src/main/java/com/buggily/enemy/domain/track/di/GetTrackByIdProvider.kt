package com.buggily.enemy.domain.track.di

import com.buggily.enemy.data.track.TrackRepositable
import com.buggily.enemy.domain.track.GetTrackById
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetTrackByIdProvider {

    @Provides
    fun provides(
        trackRepository: TrackRepositable,
    ): GetTrackById = GetTrackById(
        trackRepository = trackRepository,
    )
}
