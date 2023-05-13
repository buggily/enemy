package com.buggily.enemy.domain.navigation.di

import com.buggily.enemy.domain.navigation.Navigate
import com.buggily.enemy.domain.navigation.NavigateToAlbum
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object NavigateToAlbumProvider {

    @Provides
    fun provides(
        navigate: Navigate,
    ): NavigateToAlbum = NavigateToAlbum(
        navigate = navigate,
    )
}
