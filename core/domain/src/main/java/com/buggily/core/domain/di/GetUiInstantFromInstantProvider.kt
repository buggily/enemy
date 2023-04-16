package com.buggily.core.domain.di

import com.buggily.core.domain.GetInstantText
import com.buggily.core.domain.GetInstantWithMetadataFromInstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetUiInstantFromInstantProvider {

    @Provides
    fun provides(
        getInstantText: GetInstantText,
    ): GetInstantWithMetadataFromInstant = GetInstantWithMetadataFromInstant(
        getInstantText = getInstantText,
    )
}
