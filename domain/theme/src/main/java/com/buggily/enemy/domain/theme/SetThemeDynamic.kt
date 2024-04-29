package com.buggily.enemy.domain.theme

import com.buggily.enemy.data.theme.ThemeRepositable

class SetThemeDynamic(
    private val themeRepository: ThemeRepositable,
) {

    suspend operator fun invoke(
        dynamic: ThemeUi.Dynamic,
    ) = themeRepository.set(
        dynamic = dynamic.to(),
    )
}
