package com.buggily.enemy.domain.navigation.di

import com.buggily.enemy.domain.navigation.Navigate
import com.buggily.enemy.domain.navigation.NavigateFromPlaylistsToPlaylist
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object NavigateFromPlaylistsToPlaylistProvider {

    @Provides
    fun provides(
        navigate: Navigate,
    ): NavigateFromPlaylistsToPlaylist = NavigateFromPlaylistsToPlaylist(
        navigate = navigate,
    )
}
