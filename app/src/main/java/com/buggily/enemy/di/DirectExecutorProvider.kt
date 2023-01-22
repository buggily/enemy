package com.buggily.enemy.di

import com.google.common.util.concurrent.MoreExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executor

@Module
@InstallIn(SingletonComponent::class)
object DirectExecutorProvider {

    @Provides
    @DirectExecutorQualifier
    fun provides(): Executor = MoreExecutors.directExecutor()
}
