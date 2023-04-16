package com.buggily.core.domain.di

import com.buggily.core.domain.GetInstant
import com.buggily.core.domain.GetInstantWithMetadata
import com.buggily.core.domain.GetInstantWithMetadataFromInstant
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
        getInstantWithMetadataFromInstant: GetInstantWithMetadataFromInstant,
    ): GetInstantWithMetadata = GetInstantWithMetadata(
        getInstant = getInstant,
        getInstantWithMetadataFromInstant = getInstantWithMetadataFromInstant,
    )
}
