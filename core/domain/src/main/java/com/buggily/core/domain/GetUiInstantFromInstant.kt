package com.buggily.core.domain

import com.buggily.enemy.core.model.UiInstant
import kotlinx.datetime.Instant

class GetUiInstantFromInstant(
    private val getInstantText: GetInstantText,
) {

    operator fun invoke(
        instant: Instant,
    ): UiInstant = UiInstant(
        value = instant,
        text = getInstantText(instant),
    )
}
