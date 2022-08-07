package com.buggily.enemy.domain.map

interface QueryMapper<Input, Output> : UnidirectionalMapper<Input, Output?> {

    fun mapToResult(input: Input): Result<Output> = mapTo(input)?.let {
        Result.success(it)
    } ?: Result.failure(NullPointerException())
}
