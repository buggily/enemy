package com.buggily.enemy.domain.resume.di

import com.buggily.enemy.data.resume.ResumeRepositable
import com.buggily.enemy.domain.resume.GetResume
import com.buggily.enemy.domain.resume.SetResume
import com.buggily.enemy.domain.resume.SetResumeIdAndIndex
import com.buggily.enemy.domain.resume.SetResumePosition
import com.buggily.enemy.domain.resume.UseResume
import com.buggily.enemy.domain.track.GetTrackById
import com.buggily.enemy.domain.track.GetTracksByAlbumId
import com.buggily.enemy.domain.track.playlist.GetTracksByPlaylistId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DomainResumeModule {

    @Provides
    fun providesGetResume(
        resumeRepository: ResumeRepositable,
    ): GetResume = GetResume(
        resumeRepository = resumeRepository,
    )

    @Provides
    fun providesUseResume(
        getTrackById: GetTrackById,
        getTracksByAlbumId: GetTracksByAlbumId,
        getTracksByPlaylistId: GetTracksByPlaylistId,
    ): UseResume = UseResume(
        getTrackById = getTrackById,
        getTracksByAlbumId = getTracksByAlbumId,
        getTracksByPlaylistId = getTracksByPlaylistId,
    )

    @Provides
    fun providesSetResume(
        resumeRepository: ResumeRepositable,
    ): SetResume = SetResume(
        resumeRepository = resumeRepository,
    )

    @Provides
    fun providesSetResumeIdAndIndex(
        resumeRepository: ResumeRepositable,
    ): SetResumeIdAndIndex = SetResumeIdAndIndex(
        resumeRepository = resumeRepository,
    )

    @Provides
    fun providesSetResumePosition(
        resumeRepository: ResumeRepositable,
    ): SetResumePosition = SetResumePosition(
        resumeRepository = resumeRepository,
    )
}
