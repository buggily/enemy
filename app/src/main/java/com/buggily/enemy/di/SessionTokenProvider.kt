package com.buggily.enemy.di

import android.content.ComponentName
import android.content.Context
import androidx.media3.session.SessionToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SessionTokenProvider {

    @Provides
    fun provides(
        @ApplicationContext context: Context,
        componentName: ComponentName,
    ): SessionToken = SessionToken(
        context,
        componentName
    )
}
