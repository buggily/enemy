package com.buggily.enemy.core.local.di

import android.content.Context
import androidx.room.Room
import com.buggily.enemy.core.local.EnemyDatabase
import com.buggily.enemy.core.local.EnemyDatabaseable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object EnemyDatabaseableProvider {

    @Provides
    @Singleton
    fun provides(
        @ApplicationContext
        context: Context,
    ): EnemyDatabaseable = Room.databaseBuilder(
        context = context,
        klass = EnemyDatabase::class.java,
        name = NAME,
    ).build()

    private const val NAME = "enemy.db"
}
