package com.buggily.enemy.di

import android.content.ComponentName
import android.content.Context
import com.buggily.enemy.EnemyMediaSessionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ComponentNameProvider {

    @Provides
    fun provides(
        @ApplicationContext context: Context,
    ): ComponentName = ComponentName(
        context,
        EnemyMediaSessionService::class.java,
    )
}
