package com.buggily.enemy.data.di.source.track

import androidx.paging.PagingConfig
import com.buggily.enemy.data.query.track.TrackQuerySource
import com.buggily.enemy.data.source.track.TrackSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TrackSourceProvider {

    @Provides
    fun provides(
        config: PagingConfig,
        source: TrackQuerySource,
    ): TrackSource = TrackSource(
        config = config,
        source = source,
    )
}
