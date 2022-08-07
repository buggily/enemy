package com.buggily.enemy.domain.di.use.album

import com.buggily.enemy.data.repository.album.AlbumRepositable
import com.buggily.enemy.domain.map.album.AlbumMapper
import com.buggily.enemy.domain.use.album.GetAlbumByAlbumId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetAlbumByAlbumIdProvider {

    @Provides
    fun provides(
        repository: AlbumRepositable,
        mapper: AlbumMapper,
    ): GetAlbumByAlbumId = GetAlbumByAlbumId(
        repository = repository,
        mapper = mapper,
    )
}
