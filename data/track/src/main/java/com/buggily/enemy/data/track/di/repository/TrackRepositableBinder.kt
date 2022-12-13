package com.buggily.enemy.data.track.di.repository

import com.buggily.enemy.data.track.repository.TrackRepositable
import com.buggily.enemy.data.track.repository.TrackRepository
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
