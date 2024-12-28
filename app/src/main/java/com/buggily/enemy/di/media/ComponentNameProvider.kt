package com.buggily.enemy.di.media

import android.content.ComponentName
import android.content.Context
import com.buggily.enemy.EnemyMediaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object ComponentNameProvider {

    @Provides
    fun provides(
        @ApplicationContext context: Context,
    ): ComponentName = ComponentName(
        context,
        EnemyMediaService::class.java,
    )
}
