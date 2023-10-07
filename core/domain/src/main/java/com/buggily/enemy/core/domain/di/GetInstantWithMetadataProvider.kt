package com.buggily.enemy.core.domain.di

import com.buggily.enemy.core.domain.GetInstant
import com.buggily.enemy.core.domain.GetInstantWithMetadata
import com.buggily.enemy.core.domain.GetInstantWithMetadataFromInstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetInstantWithMetadataProvider {

    @Provides
    fun provides(
        getInstant: GetInstant,
        getInstantWithMetadataFromInstant: GetInstantWithMetadataFromInstant,
    ): GetInstantWithMetadata = GetInstantWithMetadata(
        getInstant = getInstant,
        getInstantWithMetadataFromInstant = getInstantWithMetadataFromInstant,
    )
}
