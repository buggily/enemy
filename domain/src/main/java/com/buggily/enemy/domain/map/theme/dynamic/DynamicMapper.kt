package com.buggily.enemy.domain.map.theme.dynamic

import com.buggily.enemy.domain.map.BidirectionalMapper
import com.buggily.enemy.data.theme.Theme.Dynamic as Input
import com.buggily.enemy.domain.theme.Theme.Dynamic as Output

class DynamicMapper : BidirectionalMapper<Input, Output> {

    override fun mapTo(input: Input): Output = when (input) {
        is Input.On -> Output.On
        is Input.Off -> Output.Off
        is Input.Default -> Output.Default
    }

    override fun mapFrom(output: Output): Input = when (output) {
        is Output.On -> Input.On
        is Output.Off -> Input.Off
        is Output.Default -> Input.Default
    }
}
