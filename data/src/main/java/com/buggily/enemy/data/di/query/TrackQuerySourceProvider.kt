package com.buggily.enemy.data.di.query

import android.content.ContentResolver
import com.buggily.enemy.data.query.track.TrackQuerySource
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
