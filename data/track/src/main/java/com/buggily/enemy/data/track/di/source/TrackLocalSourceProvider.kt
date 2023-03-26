package com.buggily.enemy.data.track.di.source

import androidx.paging.PagingConfig
import com.buggily.enemy.data.track.source.TrackLocalSource
import com.buggily.enemy.local.track.TrackDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TrackLocalSourceProvider {

    @Provides
    fun provides(
        config: PagingConfig,
        dao: TrackDao,
    ): TrackLocalSource = TrackLocalSource(
        config = config,
        dao = dao,
    )
}
