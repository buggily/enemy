package com.buggily.enemy.domain.navigation.di

import com.buggily.enemy.domain.navigation.Navigate
import com.buggily.enemy.domain.navigation.NavigateToTrackPlaylistPicker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigateToTrackPlaylistPickerProvider {

    @Provides
    fun provides(
        navigate: Navigate,
    ): NavigateToTrackPlaylistPicker = NavigateToTrackPlaylistPicker(
        navigate = navigate,
    )
}
