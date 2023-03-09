package com.buggily.enemy.core.local.di

import android.content.Context
import androidx.room.Room
import com.buggily.enemy.core.local.EnemyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EnemyDatabaseProvider {

    @Provides
    @Singleton
    fun provides(
        @ApplicationContext context: Context,
    ): EnemyDatabase = Room.databaseBuilder(
        context,
        EnemyDatabase::class.java,
        NAME,
    ).build()

    private const val NAME = "enemy_database"
}
