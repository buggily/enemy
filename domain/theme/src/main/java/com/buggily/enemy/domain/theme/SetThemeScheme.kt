package com.buggily.enemy.domain.theme

import com.buggily.enemy.data.theme.Theme
import com.buggily.enemy.data.theme.ThemeRepositable

class SetThemeScheme(
    private val repository: ThemeRepositable,
) {

    suspend operator fun invoke(
        scheme: Theme.Scheme,
    ) = repository.set(
        scheme = scheme,
    )
}
