package com.buggily.enemy.domain.theme

import com.buggily.enemy.data.theme.ThemeRepositable

class SetThemeScheme(
    private val themeRepository: ThemeRepositable,
) {

    suspend operator fun invoke(
        scheme: ThemeUi.Scheme,
    ) = themeRepository.set(
        scheme = scheme.to(),
    )
}
