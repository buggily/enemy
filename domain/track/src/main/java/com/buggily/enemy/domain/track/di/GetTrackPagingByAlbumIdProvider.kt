package com.buggily.enemy.domain.track.di

import com.buggily.enemy.data.track.repository.TrackRepositable
import com.buggily.enemy.domain.track.GetTrackPagingByAlbumId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetTrackPagingByAlbumIdProvider {

    @Provides
    fun provides(
        repository: TrackRepositable,
    ): GetTrackPagingByAlbumId = GetTrackPagingByAlbumId(
        repository = repository,
    )
}
