package com.buggily.enemy.domain.di.use.album

import com.buggily.enemy.data.repository.album.AlbumRepositable
import com.buggily.enemy.domain.map.album.AlbumMapper
import com.buggily.enemy.domain.use.album.GetAlbumsPaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetAlbumsPagingProvider {

    @Provides
    fun provides(
        repository: AlbumRepositable,
        mapper: AlbumMapper,
    ): GetAlbumsPaging = GetAlbumsPaging(
        repository = repository,
        mapper = mapper,
    )
}
