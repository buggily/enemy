package com.buggily.enemy.external.album.di

import android.content.ContentResolver
import com.buggily.enemy.external.album.ExternalAlbumQuerySource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object ExternalAlbumQuerySourceProvider {

    @Provides
    fun provides(
        contentResolver: ContentResolver,
    ): ExternalAlbumQuerySource = ExternalAlbumQuerySource(
        contentResolver = contentResolver,
    )
}
