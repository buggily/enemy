package com.buggily.enemy.data.di.source.track

import com.buggily.enemy.data.source.track.TrackSource
import com.buggily.enemy.data.source.track.TrackSourceable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface TrackSourceableBinder {

    @Binds
    fun binds(
        source: TrackSource,
    ): TrackSourceable
}
