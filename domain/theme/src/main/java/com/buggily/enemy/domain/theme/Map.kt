package com.buggily.enemy.domain.theme

import com.buggily.enemy.data.theme.Theme

fun Theme.toUi(): ThemeUi = ThemeUi(
    scheme = scheme.toUi(),
    dynamic = dynamic.toUi(),
)

fun Theme.Scheme.toUi(): ThemeUi.Scheme = when (this) {
    is Theme.Scheme.Default -> ThemeUi.Scheme.Default
    is Theme.Scheme.Light -> ThemeUi.Scheme.Light
    is Theme.Scheme.Dark -> ThemeUi.Scheme.Dark
}

fun Theme.Dynamic.toUi(): ThemeUi.Dynamic = when (this) {
    is Theme.Dynamic.On -> ThemeUi.Dynamic.On
    is Theme.Dynamic.Off -> ThemeUi.Dynamic.Off
}

fun ThemeUi.to(): Theme = Theme(
    scheme = scheme.to(),
    dynamic = dynamic.to(),
)

fun ThemeUi.Dynamic.to(): Theme.Dynamic = when (this) {
    is ThemeUi.Dynamic.On -> Theme.Dynamic.On
    is ThemeUi.Dynamic.Off -> Theme.Dynamic.Off
}

fun ThemeUi.Scheme.to(): Theme.Scheme = when (this) {
    is ThemeUi.Scheme.Default -> Theme.Scheme.Default
    is ThemeUi.Scheme.Light -> Theme.Scheme.Light
    is ThemeUi.Scheme.Dark -> Theme.Scheme.Dark
}
