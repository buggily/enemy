package com.buggily.enemy.domain.navigation.di

import com.buggily.enemy.core.navigation.NavigationOrchestratable
import com.buggily.enemy.domain.navigation.Navigate
import com.buggily.enemy.domain.navigation.NavigateBack
import com.buggily.enemy.domain.navigation.NavigateBackFromController
import com.buggily.enemy.domain.navigation.NavigateBackFromCreatePlaylist
import com.buggily.enemy.domain.navigation.NavigateBackFromEditPlaylist
import com.buggily.enemy.domain.navigation.NavigateBackFromPlaylistPicker
import com.buggily.enemy.domain.navigation.NavigateBackFromPlaylistTrackPicker
import com.buggily.enemy.domain.navigation.NavigateBackFromTrackPlaylistPicker
import com.buggily.enemy.domain.navigation.NavigateFromOrientationToBrowse
import com.buggily.enemy.domain.navigation.NavigateToAlbum
import com.buggily.enemy.domain.navigation.NavigateToController
import com.buggily.enemy.domain.navigation.NavigateToCreatePlaylist
import com.buggily.enemy.domain.navigation.NavigateToEditPlaylist
import com.buggily.enemy.domain.navigation.NavigateToOrientation
import com.buggily.enemy.domain.navigation.NavigateToPlaylist
import com.buggily.enemy.domain.navigation.NavigateToPlaylistPicker
import com.buggily.enemy.domain.navigation.NavigateToPlaylistTrackPicker
import com.buggily.enemy.domain.navigation.NavigateToPreferences
import com.buggily.enemy.domain.navigation.NavigateToTrackPicker
import com.buggily.enemy.domain.navigation.NavigateToTrackPlaylistPicker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object NavigationProvider {

    @Provides
    fun providesNavigate(
        orchestrator: NavigationOrchestratable,
    ): Navigate = Navigate(
        orchestrator = orchestrator,
    )

    @Provides
    fun providesNavigateBack(
        navigate: Navigate,
    ): NavigateBack = NavigateBack(
        navigate = navigate,
    )

    @Provides
    fun providesNavigateBackFromController(
        navigate: Navigate,
    ): NavigateBackFromController = NavigateBackFromController(
        navigate = navigate,
    )

    @Provides
    fun providesNavigateBackFromCreatePlaylist(
        navigate: Navigate,
    ): NavigateBackFromCreatePlaylist = NavigateBackFromCreatePlaylist(
        navigate = navigate,
    )

    @Provides
    fun providesNavigateBackFromEditPlaylist(
        navigate: Navigate,
    ): NavigateBackFromEditPlaylist = NavigateBackFromEditPlaylist(
        navigate = navigate,
    )

    @Provides
    fun providesNavigateBackFromPlaylistPicker(
        navigate: Navigate,
    ): NavigateBackFromPlaylistPicker = NavigateBackFromPlaylistPicker(
        navigate = navigate,
    )

    @Provides
    fun providesNavigateBackFromPlaylistTrackPicker(
        navigate: Navigate,
    ): NavigateBackFromPlaylistTrackPicker = NavigateBackFromPlaylistTrackPicker(
        navigate = navigate,
    )

    @Provides
    fun providesNavigateBackFromTrackPlaylistPicker(
        navigate: Navigate,
    ): NavigateBackFromTrackPlaylistPicker = NavigateBackFromTrackPlaylistPicker(
        navigate = navigate,
    )

    @Provides
    fun providesNavigateFromOrientationToBrowse(
        navigate: Navigate,
    ): NavigateFromOrientationToBrowse = NavigateFromOrientationToBrowse(
        navigate = navigate,
    )

    @Provides
    fun providesNavigateToAlbum(
        navigate: Navigate,
    ): NavigateToAlbum = NavigateToAlbum(
        navigate = navigate,
    )

    @Provides
    fun providesNavigateToController(
        navigate: Navigate,
    ): NavigateToController = NavigateToController(
        navigate = navigate,
    )

    @Provides
    fun providesNavigateToCreatePlaylist(
        navigate: Navigate,
    ): NavigateToCreatePlaylist = NavigateToCreatePlaylist(
        navigate = navigate,
    )

    @Provides
    fun providesNavigateToEditPlaylist(
         navigate: Navigate,
    ): NavigateToEditPlaylist = NavigateToEditPlaylist(
        navigate = navigate,
    )

    @Provides
    fun providesNavigateToOrientation(
        navigate: Navigate,
    ): NavigateToOrientation = NavigateToOrientation(
        navigate = navigate,
    )

    @Provides
    fun providesNavigateToPlaylistPicker(
        navigate: Navigate,
    ): NavigateToPlaylistPicker = NavigateToPlaylistPicker(
        navigate = navigate,
    )

    @Provides
    fun providesNavigateToPlaylist(
        navigate: Navigate,
    ): NavigateToPlaylist = NavigateToPlaylist(
        navigate = navigate,
    )

    @Provides
    fun providesNavigateToPlaylistTrackPicker(
        navigate: Navigate,
    ): NavigateToPlaylistTrackPicker = NavigateToPlaylistTrackPicker(
        navigate = navigate,
    )

    @Provides
    fun providesNavigateToPreferences(
        navigate: Navigate,
    ): NavigateToPreferences = NavigateToPreferences(
        navigate = navigate,
    )

    @Provides
    fun providesNavigateToTrackPicker(
        navigate: Navigate,
    ): NavigateToTrackPicker = NavigateToTrackPicker(
        navigate = navigate,
    )

    @Provides
    fun providesNavigateToTrackPlaylistPicker(
        navigate: Navigate,
    ): NavigateToTrackPlaylistPicker = NavigateToTrackPlaylistPicker(
        navigate = navigate,
    )
}
