package com.buggily.enemy.core.domain

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class GetDuration {

    operator fun invoke(
        duration: Long,
    ): Duration = duration.toDuration(
        unit =DurationUnit.MILLISECONDS,
    )

    operator fun invoke(
        duration: Long,
        unit: DurationUnit,
    ): Duration = duration.toDuration(
        unit = unit,
    )
}
