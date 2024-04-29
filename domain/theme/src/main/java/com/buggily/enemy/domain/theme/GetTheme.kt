package com.buggily.enemy.domain.theme

import com.buggily.enemy.data.theme.ThemeRepositable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTheme(
    private val themeRepository: ThemeRepositable,
) {

    operator fun invoke(): Flow<ThemeUi> = themeRepository.get().map {
        it.toUi()
    }
}
