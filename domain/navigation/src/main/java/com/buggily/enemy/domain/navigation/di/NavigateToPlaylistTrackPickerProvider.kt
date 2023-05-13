package com.buggily.enemy.domain.navigation.di

import com.buggily.enemy.domain.navigation.Navigate
import com.buggily.enemy.domain.navigation.NavigateToPlaylistTrackPicker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigateToPlaylistTrackPickerProvider {

    @Provides
    fun provides(
        navigate: Navigate,
    ): NavigateToPlaylistTrackPicker = NavigateToPlaylistTrackPicker(
        navigate = navigate,
    )
}
