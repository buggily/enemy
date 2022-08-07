package com.buggily.enemy.data.di.paging

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
        pageSize = PAGE_SIZE,
        maxSize = MAX_SIZE,
        enablePlaceholders = ENABLE_PLACEHOLDERS,
    )

    private const val PAGE_SIZE = 30
    private const val MAX_SIZE = PAGE_SIZE * 3
    private const val ENABLE_PLACEHOLDERS = true
}
