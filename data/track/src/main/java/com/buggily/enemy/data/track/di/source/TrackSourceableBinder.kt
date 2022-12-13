package com.buggily.enemy.data.track.di.source

import com.buggily.enemy.data.track.source.TrackSource
import com.buggily.enemy.data.track.source.TrackSourceable
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
