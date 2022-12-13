package com.buggily.core.domain.di

import com.buggily.core.domain.GetDuration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Locale

@Module
@InstallIn(SingletonComponent::class)
object GetDurationProvider {

    @Provides
    fun provides(locale: Locale): GetDuration = GetDuration(locale)
}
