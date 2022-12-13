package com.buggily.enemy.domain.theme

import com.buggily.enemy.data.theme.repository.ThemeRepositable

class GetTheme(
    private val repository: ThemeRepositable,
) {

    operator fun invoke() = repository.get()
}
