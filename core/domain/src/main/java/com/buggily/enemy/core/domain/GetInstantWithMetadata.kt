package com.buggily.enemy.core.domain

import com.buggily.enemy.core.data.InstantWithMetadata

class GetInstantWithMetadata(
    private val getInstant: GetInstant,
    private val getInstantWithMetadataFromInstant: GetInstantWithMetadataFromInstant,
) {

    operator fun invoke(): InstantWithMetadata = getInstantWithMetadataFromInstant(
        instant = getInstant(),
    )
}
