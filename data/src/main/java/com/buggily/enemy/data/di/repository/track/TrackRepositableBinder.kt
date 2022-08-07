package com.buggily.enemy.data.di.repository.track

import com.buggily.enemy.data.repository.track.TrackRepositable
import com.buggily.enemy.data.repository.track.TrackRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface TrackRepositableBinder {

    @Binds
    fun binds(
        repository: TrackRepository,
    ): TrackRepositable
}
