package com.buggily.core.domain.di

import com.buggily.core.domain.GetInstant
import com.buggily.core.domain.GetUiInstant
import com.buggily.core.domain.GetUiInstantFromInstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetUiInstantProvider {

    @Provides
    fun provides(
        getInstant: GetInstant,
        getUiInstantFromInstant: GetUiInstantFromInstant,
    ): GetUiInstant = GetUiInstant(
        getInstant = getInstant,
        getUiInstantFromInstant = getUiInstantFromInstant,
    )
}
