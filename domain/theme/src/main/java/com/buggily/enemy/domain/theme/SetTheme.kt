package com.buggily.enemy.domain.theme

import com.buggily.enemy.data.theme.Theme
import com.buggily.enemy.data.theme.ThemeRepositable

class SetTheme(
    private val repository: ThemeRepositable,
) {

    suspend operator fun invoke(
        theme: Theme,
    ) = repository.set(
        theme = theme,
    )
}
