package com.buggily.core.domain

import com.buggily.enemy.core.model.UiInstant
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetUiInstant(
    private val getInstant: GetInstant,
    private val getUiInstantFromInstant: GetUiInstantFromInstant,
) {

    operator fun invoke(): UiInstant = getUiInstantFromInstant(getInstant())
}
