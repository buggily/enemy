package com.buggily.enemy.data.theme

import com.buggily.enemy.local.preferences.LocalTheme

fun LocalTheme.to(): Theme = Theme(
    scheme = scheme.to(),
    dynamic = dynamic.to(),
)

fun LocalTheme.Scheme.to(): Theme.Scheme = when (this) {
    is LocalTheme.Scheme.Default -> Theme.Scheme.Default
    is LocalTheme.Scheme.Light -> Theme.Scheme.Light
    is LocalTheme.Scheme.Dark -> Theme.Scheme.Dark
}

fun LocalTheme.Dynamic.to(): Theme.Dynamic = when (this) {
    is LocalTheme.Dynamic.Default -> Theme.Dynamic.Default
    is LocalTheme.Dynamic.On -> Theme.Dynamic.On
    is LocalTheme.Dynamic.Off -> Theme.Dynamic.Off
}

fun Theme.toLocal(): LocalTheme = LocalTheme(
    scheme = scheme.toLocal(),
    dynamic = dynamic.toLocal(),
)

fun Theme.Scheme.toLocal(): LocalTheme.Scheme = when (this) {
    is Theme.Scheme.Default -> LocalTheme.Scheme.Default
    is Theme.Scheme.Light -> LocalTheme.Scheme.Light
    is Theme.Scheme.Dark -> LocalTheme.Scheme.Dark
}

fun Theme.Dynamic.toLocal(): LocalTheme.Dynamic = when (this) {
    is Theme.Dynamic.Default -> LocalTheme.Dynamic.Default
    is Theme.Dynamic.On -> LocalTheme.Dynamic.On
    is Theme.Dynamic.Off -> LocalTheme.Dynamic.Off
}
