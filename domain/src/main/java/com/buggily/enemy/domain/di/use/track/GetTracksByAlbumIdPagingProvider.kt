package com.buggily.enemy.domain.di.use.track

import com.buggily.enemy.data.repository.track.TrackRepositable
import com.buggily.enemy.domain.map.track.TrackMapper
import com.buggily.enemy.domain.use.track.GetTracksByAlbumIdPaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetTracksByAlbumIdPagingProvider {

    @Provides
    fun provides(
        repository: TrackRepositable,
        mapper: TrackMapper,
    ): GetTracksByAlbumIdPaging = GetTracksByAlbumIdPaging(
        repository = repository,
        mapper = mapper,
    )
}
