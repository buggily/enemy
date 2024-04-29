package com.buggily.enemy.domain.theme

import com.buggily.enemy.data.theme.ThemeRepositable

class SetTheme(
    private val themeRepository: ThemeRepositable,
) {

    suspend operator fun invoke(
        theme: ThemeUi,
    ) = themeRepository.set(
        theme = theme.to(),
    )
}
