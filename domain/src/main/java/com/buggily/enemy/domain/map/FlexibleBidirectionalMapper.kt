package com.buggily.enemy.domain.map

interface FlexibleBidirectionalMapper<InputTo, OutputTo, InputFrom, OutputFrom> : UnidirectionalMapper<InputTo, OutputTo> {
    fun mapFrom(output: OutputFrom): InputFrom
}
