package com.buggily.enemy.data.di.query

import android.content.ContentResolver
import com.buggily.enemy.data.query.album.AlbumQuerySource
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
