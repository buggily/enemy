package com.buggily.enemy.domain.navigation.di

import com.buggily.enemy.domain.navigation.Navigate
import com.buggily.enemy.domain.navigation.NavigateFromAlbumsToAlbum
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object NavigateFromAlbumsToAlbumProvider {

    @Provides
    fun provides(
        navigate: Navigate,
    ): NavigateFromAlbumsToAlbum = NavigateFromAlbumsToAlbum(
        navigate = navigate,
    )
}
