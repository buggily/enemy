package com.buggily.enemy.domain.di.map.track

import com.buggily.enemy.domain.map.track.TrackMapper
import com.buggily.enemy.domain.use.GetDuration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TrackMapperProvider {

    @Provides
    fun provides(getDuration: GetDuration): TrackMapper = TrackMapper(getDuration)
}
