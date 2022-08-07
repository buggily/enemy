package com.buggily.enemy.domain.map

interface UnidirectionalMapper<Input, Output> {
    fun mapTo(input: Input): Output
}
