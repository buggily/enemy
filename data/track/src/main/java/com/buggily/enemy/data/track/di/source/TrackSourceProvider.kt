package com.buggily.enemy.data.track.di.source

import androidx.paging.PagingConfig
import com.buggily.enemy.data.track.source.TrackSource
import com.buggily.enemy.local.track.TrackQuerySource
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
