package com.buggily.enemy.domain.di.map.album

import com.buggily.enemy.domain.map.album.AlbumMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AlbumMapperProvider {

    @Provides
    fun provides(): AlbumMapper = AlbumMapper()
}
