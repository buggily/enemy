package com.buggily.enemy.core.query.di

import android.content.ContentResolver
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ContentResolverProvider {

    @Provides
    fun provides(
        @ApplicationContext context: Context,
    ): ContentResolver = context.contentResolver
}
