package com.buggily.enemy.data.track.di.repository

import com.buggily.core.domain.GetUiDuration
import com.buggily.enemy.data.track.repository.TrackRepository
import com.buggily.enemy.data.track.source.TrackSourceable
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
        getUiDuration: GetUiDuration,
    ): TrackRepository = TrackRepository(
        source = source,
        getUiDuration = getUiDuration,
    )
}
