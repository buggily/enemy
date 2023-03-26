package com.buggily.enemy.data.theme.ext

import com.buggily.enemy.core.model.theme.Theme
import com.buggily.enemy.local.preferences.Theme as LocalTheme

fun LocalTheme.map(): Theme = Theme(
    scheme = scheme.map(),
    dynamic = dynamic.map(),
)

fun LocalTheme.Scheme.map(): Theme.Scheme = when (this) {
    is LocalTheme.Scheme.Default -> Theme.Scheme.Default
    is LocalTheme.Scheme.Light -> Theme.Scheme.Light
    is LocalTheme.Scheme.Dark -> Theme.Scheme.Dark
}

fun LocalTheme.Dynamic.map(): Theme.Dynamic = when (this) {
    is LocalTheme.Dynamic.Default -> Theme.Dynamic.Default
    is LocalTheme.Dynamic.On -> Theme.Dynamic.On
    is LocalTheme.Dynamic.Off -> Theme.Dynamic.Off
}


fun Theme.map(): LocalTheme = LocalTheme(
    scheme = scheme.map(),
    dynamic = dynamic.map(),
)

fun Theme.Scheme.map(): LocalTheme.Scheme = when (this) {
    is Theme.Scheme.Default -> LocalTheme.Scheme.Default
    is Theme.Scheme.Light -> LocalTheme.Scheme.Light
    is Theme.Scheme.Dark -> LocalTheme.Scheme.Dark
}

fun Theme.Dynamic.map(): LocalTheme.Dynamic = when (this) {
    is Theme.Dynamic.Default -> LocalTheme.Dynamic.Default
    is Theme.Dynamic.On -> LocalTheme.Dynamic.On
    is Theme.Dynamic.Off -> LocalTheme.Dynamic.Off
}
