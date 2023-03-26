package com.buggily.enemy.data.track.di.source

import com.buggily.enemy.data.track.source.TrackLocalSource
import com.buggily.enemy.data.track.source.TrackLocalSourceable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface TrackLocalSourceableBinder {

    @Binds
    fun binds(
        source: TrackLocalSource,
    ): TrackLocalSourceable
}
