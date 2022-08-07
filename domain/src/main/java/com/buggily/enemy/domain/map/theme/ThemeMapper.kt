package com.buggily.enemy.domain.map.theme

import com.buggily.enemy.domain.map.BidirectionalMapper
import com.buggily.enemy.data.Theme as Input
import com.buggily.enemy.domain.theme.Theme as Output

class ThemeMapper : BidirectionalMapper<Input, Output> {

    override fun mapTo(input: Input): Output = when (input) {
        is Input.Light -> Output.Light
        is Input.Dark -> Output.Dark
        is Input.Default -> Output.Default
    }

    override fun mapFrom(output: Output): Input = when (output) {
        is Output.Light -> Input.Light
        is Output.Dark -> Input.Dark
        is Output.Default -> Input.Default
    }
}
