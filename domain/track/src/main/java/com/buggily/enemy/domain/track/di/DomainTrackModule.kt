package com.buggily.enemy.domain.track.di

import com.buggily.enemy.core.domain.GetDuration
import com.buggily.enemy.core.domain.GetDurationText
import com.buggily.enemy.data.track.TrackRepositable
import com.buggily.enemy.domain.track.DeleteTracks
import com.buggily.enemy.domain.track.GetTrackById
import com.buggily.enemy.domain.track.GetTrackPaging
import com.buggily.enemy.domain.track.GetTrackPagingByAlbumId
import com.buggily.enemy.domain.track.GetTrackPagingByPopularity
import com.buggily.enemy.domain.track.GetTrackPagingByRecency
import com.buggily.enemy.domain.track.GetTracksByAlbumId
import com.buggily.enemy.domain.track.IncrementTrackPlaysById
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DomainTrackModule {

    @Provides
    fun providesGetTrackById(
        trackRepository: TrackRepositable,
        getDurationText: GetDurationText,
        getDuration: GetDuration,
    ): GetTrackById = GetTrackById(
        trackRepository = trackRepository,
        getDurationText = getDurationText,
        getDuration = getDuration,
    )

    @Provides
    fun providesGetTrackPaging(
        trackRepository: TrackRepositable,
        getDurationText: GetDurationText,
        getDuration: GetDuration,
    ): GetTrackPaging = GetTrackPaging(
        trackRepository = trackRepository,
        getDurationText = getDurationText,
        getDuration = getDuration,
    )

    @Provides
    fun providesGetTrackPagingByAlbumId(
        trackRepository: TrackRepositable,
        getDurationText: GetDurationText,
        getDuration: GetDuration,
    ): GetTrackPagingByAlbumId = GetTrackPagingByAlbumId(
        trackRepository = trackRepository,
        getDurationText = getDurationText,
        getDuration = getDuration,
    )

    @Provides
    fun providesGetTrackPagingByPopularity(
        trackRepository: TrackRepositable,
        getDurationText: GetDurationText,
        getDuration: GetDuration,
    ): GetTrackPagingByPopularity = GetTrackPagingByPopularity(
        trackRepository = trackRepository,
        getDurationText = getDurationText,
        getDuration = getDuration,
    )

    @Provides
    fun providesGetTrackPagingByRecency(
        trackRepository: TrackRepositable,
        getDurationText: GetDurationText,
        getDuration: GetDuration,
    ): GetTrackPagingByRecency = GetTrackPagingByRecency(
        trackRepository = trackRepository,
        getDurationText = getDurationText,
        getDuration = getDuration,
    )

    @Provides
    fun providesGetTracksByAlbumId(
        trackRepository: TrackRepositable,
        getDurationText: GetDurationText,
        getDuration: GetDuration,
    ): GetTracksByAlbumId = GetTracksByAlbumId(
        trackRepository = trackRepository,
        getDurationText = getDurationText,
        getDuration = getDuration,
    )

    @Provides
    fun providesIncrementTrackPlaysById(
        trackRepository: TrackRepositable,
    ): IncrementTrackPlaysById = IncrementTrackPlaysById(
        trackRepository = trackRepository,
    )

    @Provides
    fun providesDeleteTracks(
        trackRepository: TrackRepositable,
    ): DeleteTracks = DeleteTracks(
        trackRepository = trackRepository,
    )
}
