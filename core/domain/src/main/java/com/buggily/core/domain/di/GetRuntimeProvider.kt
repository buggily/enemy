package com.buggily.core.domain.di

import com.buggily.core.domain.GetRuntime
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Locale

@Module
@InstallIn(SingletonComponent::class)
object GetRuntimeProvider {

    @Provides
    fun provides(locale: Locale): GetRuntime = GetRuntime(locale)
}
