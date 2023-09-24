package com.buggily.enemy.ui.theme

import com.buggily.enemy.data.theme.Theme

fun Theme.Scheme.to(
    isDynamic: Boolean,
    isSystemInDarkTheme: Boolean,
): EnemyPalette.Theme = when (this) {
    is Theme.Scheme.Default -> EnemyPalette.Theme.Default(
        isDynamic = isDynamic,
        isSystemInDarkTheme = isSystemInDarkTheme,
    )

    is Theme.Scheme.Light -> EnemyPalette.Theme.Light(
        isDynamic = isDynamic,
    )

    is Theme.Scheme.Dark -> EnemyPalette.Theme.Dark(
        isDynamic = isDynamic,
    )
}