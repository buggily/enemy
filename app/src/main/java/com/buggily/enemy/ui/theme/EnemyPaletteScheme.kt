package com.buggily.enemy.ui.theme

import com.buggily.enemy.domain.theme.ThemeUi

fun ThemeUi.Scheme.to(
    isDynamic: Boolean,
    isSystemInDarkTheme: Boolean,
): EnemyPalette.Theme = when (this) {
    is ThemeUi.Scheme.Default -> EnemyPalette.Theme.Default(
        isDynamic = isDynamic,
        isSystemInDarkTheme = isSystemInDarkTheme,
    )

    is ThemeUi.Scheme.Light -> EnemyPalette.Theme.Light(
        isDynamic = isDynamic,
    )

    is ThemeUi.Scheme.Dark -> EnemyPalette.Theme.Dark(
        isDynamic = isDynamic,
    )
}
