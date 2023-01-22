package com.buggily.core.domain.di

import com.buggily.core.domain.GetInstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetInstantProvider {

    @Provides
    fun provides(): GetInstant = GetInstant()
}
