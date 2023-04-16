package com.buggily.enemy.external.track.di

import android.content.ContentResolver
import com.buggily.enemy.external.track.ExternalTrackQuerySource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object ExternalTrackQuerySourceProvider {

    @Provides
    fun provides(
        contentResolver: ContentResolver,
    ): ExternalTrackQuerySource = ExternalTrackQuerySource(
        contentResolver = contentResolver,
    )
}
