package com.buggily.enemy.data.playlist.di.repository

import com.buggily.core.domain.GetUiInstantFromInstant
import com.buggily.enemy.data.playlist.repository.PlaylistRepository
import com.buggily.enemy.data.playlist.source.PlaylistSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PlaylistRepositoryProvider {

    @Provides
    fun provides(
        source: PlaylistSourceable,
        getUiInstantFromInstant: GetUiInstantFromInstant,
    ): PlaylistRepository = PlaylistRepository(
        source = source,
        getUiInstantFromInstant = getUiInstantFromInstant,
    )
}
