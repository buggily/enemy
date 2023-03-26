package com.buggily.enemy.data.track.di.source

import androidx.paging.PagingConfig
import com.buggily.enemy.data.track.source.TrackExternalSource
import com.buggily.enemy.external.track.TrackQuerySource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TrackExternalSourceProvider {

    @Provides
    fun provides(
        config: PagingConfig,
        source: TrackQuerySource,
    ): TrackExternalSource = TrackExternalSource(
        config = config,
        source = source,
    )
}
