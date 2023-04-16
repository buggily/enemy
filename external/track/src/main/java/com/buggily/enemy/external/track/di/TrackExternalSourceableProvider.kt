package com.buggily.enemy.external.track.di

import androidx.paging.PagingConfig
import com.buggily.enemy.external.track.ExternalTrackQuerySource
import com.buggily.enemy.external.track.ExternalTrackSource
import com.buggily.enemy.external.track.ExternalTrackSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object TrackExternalSourceableProvider {

    @Provides
    fun provides(
        config: PagingConfig,
        externalTrackQuerySource: ExternalTrackQuerySource,
    ): ExternalTrackSourceable = ExternalTrackSource(
        config = config,
        externalTrackQuerySource = externalTrackQuerySource,
    )
}
