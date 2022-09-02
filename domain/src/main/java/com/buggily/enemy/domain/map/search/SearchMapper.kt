package com.buggily.enemy.domain.map.search

import com.buggily.enemy.domain.map.BidirectionalMapper
import com.buggily.enemy.data.Search as Input
import com.buggily.enemy.domain.search.Search as Output

class SearchMapper : BidirectionalMapper<Input, Output> {

    override fun mapTo(input: Input): Output {
        val value: String = input.value
        val isVisible: Boolean = input.isVisible

        return Output(
            value = value,
            isVisible = isVisible,
        )
    }

    override fun mapFrom(output: Output): Input {
        val value: String = output.value
        val isVisible: Boolean = output.isVisible

        return Input.newBuilder()
            .setValue(value)
            .setIsVisible(isVisible)
            .build()
    }
}
