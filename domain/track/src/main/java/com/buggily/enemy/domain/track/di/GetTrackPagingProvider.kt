package com.buggily.enemy.domain.track.di

import com.buggily.enemy.data.track.repository.TrackRepositable
import com.buggily.enemy.domain.track.GetTrackPaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetTrackPagingProvider {

    @Provides
    fun provides(
        repository: TrackRepositable,
    ): GetTrackPaging = GetTrackPaging(
        repository = repository,
    )
}
