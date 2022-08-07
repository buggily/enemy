package com.buggily.enemy.di.map

import com.buggily.enemy.map.TrackMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TrackMapperProvider {

    @Provides
    fun provides(): TrackMapper = TrackMapper()
}
