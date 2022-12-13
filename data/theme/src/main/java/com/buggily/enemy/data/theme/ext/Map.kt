package com.buggily.enemy.data.theme.ext

import com.buggily.enemy.core.model.theme.Theme as Output
import com.buggily.enemy.local.preferences.Theme as Input

fun Input.map(): Output = Output(
    scheme = scheme.map(),
    dynamic = dynamic.map(),
)

fun Input.Scheme.map(): Output.Scheme = when (this) {
    is Input.Scheme.Default -> Output.Scheme.Default
    is Input.Scheme.Light -> Output.Scheme.Light
    is Input.Scheme.Dark -> Output.Scheme.Dark
}

fun Input.Dynamic.map(): Output.Dynamic = when (this) {
    is Input.Dynamic.Default -> Output.Dynamic.Default
    is Input.Dynamic.On -> Output.Dynamic.On
    is Input.Dynamic.Off -> Output.Dynamic.Off
}


fun Output.map(): Input = Input(
    scheme = scheme.map(),
    dynamic = dynamic.map(),
)

fun Output.Scheme.map(): Input.Scheme = when (this) {
    is Output.Scheme.Default -> Input.Scheme.Default
    is Output.Scheme.Light -> Input.Scheme.Light
    is Output.Scheme.Dark -> Input.Scheme.Dark
}

fun Output.Dynamic.map(): Input.Dynamic = when (this) {
    is Output.Dynamic.Default -> Input.Dynamic.Default
    is Output.Dynamic.On -> Input.Dynamic.On
    is Output.Dynamic.Off -> Input.Dynamic.Off
}
