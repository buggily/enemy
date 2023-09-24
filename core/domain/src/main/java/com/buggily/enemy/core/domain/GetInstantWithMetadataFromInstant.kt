package com.buggily.enemy.core.domain

import com.buggily.enemy.core.data.InstantWithMetadata
import kotlinx.datetime.Instant

class GetInstantWithMetadataFromInstant(
    private val getInstantText: GetInstantText,
) {

    operator fun invoke(
        instant: Instant,
    ): InstantWithMetadata = InstantWithMetadata(
        value = instant,
        text = getInstantText(instant),
    )
}
