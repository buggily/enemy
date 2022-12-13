package com.buggily.enemy.domain.track.di

import com.buggily.enemy.data.track.repository.TrackRepositable
import com.buggily.enemy.domain.track.GetTracksByAlbumId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetTracksByAlbumIdProvider {

    @Provides
    fun provides(
        repository: TrackRepositable,
    ): GetTracksByAlbumId = GetTracksByAlbumId(
        repository = repository,
    )
}
