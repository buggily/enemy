package com.buggily.enemy.core.local.di

import com.buggily.enemy.core.local.EnemyDatabase
import com.buggily.enemy.core.local.EnemyDatabaseable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface EnemyDatabaseableBinder {

    @Binds
    fun binds(
        database: EnemyDatabase,
    ): EnemyDatabaseable
}
