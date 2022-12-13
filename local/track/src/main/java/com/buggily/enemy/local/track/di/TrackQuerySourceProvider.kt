package com.buggily.enemy.local.track.di

import android.content.ContentResolver
import com.buggily.enemy.local.track.TrackQuerySource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TrackQuerySourceProvider {

    @Provides
    fun provides(
        contentResolver: ContentResolver,
    ): TrackQuerySource = TrackQuerySource(
        contentResolver = contentResolver,
    )
}
