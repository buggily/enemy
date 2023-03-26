package com.buggily.enemy.data.track.di.source

import com.buggily.enemy.data.track.source.TrackExternalSource
import com.buggily.enemy.data.track.source.TrackExternalSourceable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface TrackExternalSourceableBinder {

    @Binds
    fun binds(
        source: TrackExternalSource,
    ): TrackExternalSourceable
}
