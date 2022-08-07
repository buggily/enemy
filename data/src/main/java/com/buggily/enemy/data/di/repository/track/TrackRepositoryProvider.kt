package com.buggily.enemy.data.di.repository.track

import com.buggily.enemy.data.repository.track.TrackRepository
import com.buggily.enemy.data.source.track.TrackSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TrackRepositoryProvider {

    @Provides
    fun provides(
        source: TrackSourceable,
    ): TrackRepository = TrackRepository(
        source = source,
    )
}
