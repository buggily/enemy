package com.buggily.enemy.local.album.di

import android.content.ContentResolver
import com.buggily.enemy.local.album.AlbumQuerySource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AlbumQuerySourceProvider {

    @Provides
    fun provides(
        contentResolver: ContentResolver,
    ): AlbumQuerySource = AlbumQuerySource(
        contentResolver = contentResolver,
    )
}
