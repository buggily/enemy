package com.buggily.enemy.domain.theme

import com.buggily.enemy.core.model.theme.Theme
import com.buggily.enemy.data.theme.repository.ThemeRepositable

class SetThemeScheme(
    private val repository: ThemeRepositable,
) {

    suspend operator fun invoke(scheme: Theme.Scheme) = repository.set(scheme)
}
