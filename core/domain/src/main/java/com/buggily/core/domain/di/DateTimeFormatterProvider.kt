package com.buggily.core.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.format.DateTimeFormatter

@Module
@InstallIn(SingletonComponent::class)
object DateTimeFormatterProvider {

    @Provides
    fun provides(): DateTimeFormatter = DateTimeFormatter.ISO_DATE
}
