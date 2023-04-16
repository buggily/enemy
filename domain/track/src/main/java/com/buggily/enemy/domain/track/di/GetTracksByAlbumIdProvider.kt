package com.buggily.enemy.domain.track.di

import com.buggily.enemy.data.track.TrackRepositable
import com.buggily.enemy.domain.track.GetTracksByAlbumId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object GetTracksByAlbumIdProvider {

    @Provides
    fun provides(
        repository: TrackRepositable,
    ): GetTracksByAlbumId = GetTracksByAlbumId(
        repository = repository,
    )
}
