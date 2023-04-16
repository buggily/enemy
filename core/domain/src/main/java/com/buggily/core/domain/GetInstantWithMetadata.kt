package com.buggily.core.domain

import com.buggily.enemy.core.data.InstantWithMetadata
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetInstantWithMetadata(
    private val getInstant: GetInstant,
    private val getInstantWithMetadataFromInstant: GetInstantWithMetadataFromInstant,
) {

    operator fun invoke(): InstantWithMetadata = getInstantWithMetadataFromInstant(
        instant = getInstant(),
    )
}
