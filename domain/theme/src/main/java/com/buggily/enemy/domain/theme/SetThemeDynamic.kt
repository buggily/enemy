package com.buggily.enemy.domain.theme

import com.buggily.enemy.data.theme.Theme
import com.buggily.enemy.data.theme.ThemeRepositable

class SetThemeDynamic(
    private val repository: ThemeRepositable,
) {

    suspend operator fun invoke(
        dynamic: Theme.Dynamic,
    ) = repository.set(
        dynamic = dynamic,
    )
}
