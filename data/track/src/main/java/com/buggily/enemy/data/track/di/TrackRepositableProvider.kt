package com.buggily.enemy.data.track.di

import com.buggily.enemy.core.domain.GetDurationWithMetadata
import com.buggily.enemy.core.domain.GetInstant
import com.buggily.enemy.data.track.TrackRepositable
import com.buggily.enemy.data.track.TrackRepository
import com.buggily.enemy.external.track.ExternalTrackSourceable
import com.buggily.enemy.local.track.LocalTrackSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object TrackRepositableProvider {

    @Provides
    fun provides(
        localTrackSource: LocalTrackSourceable,
        externalTrackSource: ExternalTrackSourceable,
        getInstant: GetInstant,
        getDurationWithMetadata: GetDurationWithMetadata,
    ): TrackRepositable = TrackRepository(
        localTrackSource = localTrackSource,
        externalTrackSource = externalTrackSource,
        getInstant = getInstant,
        getDurationWithMetadata = getDurationWithMetadata,
    )
}
