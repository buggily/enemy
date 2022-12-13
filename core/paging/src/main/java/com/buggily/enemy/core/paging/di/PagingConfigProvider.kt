package com.buggily.enemy.core.paging.di

import androidx.paging.PagingConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PagingConfigProvider {

    @Provides
    fun provides(): PagingConfig = PagingConfig(
        pageSize = pageSize,
        maxSize = maxSize,
        enablePlaceholders = enablePlaceholders,
    )

    private const val pageSize = 30
    private const val maxSize = pageSize * 3
    private const val enablePlaceholders = true
}
