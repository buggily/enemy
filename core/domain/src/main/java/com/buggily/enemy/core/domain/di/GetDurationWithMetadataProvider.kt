package com.buggily.enemy.core.domain.di

import com.buggily.core.domain.GetDurationWithMetadata
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Locale

@Module
@InstallIn(SingletonComponent::class)
object GetDurationWithMetadataProvider {

    @Provides
    fun provides(locale: Locale): GetDurationWithMetadata = GetDurationWithMetadata(locale)
}
