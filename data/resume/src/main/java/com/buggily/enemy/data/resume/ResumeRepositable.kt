package com.buggily.enemy.data.resume

import kotlinx.coroutines.flow.Flow

interface ResumeRepositable {

    fun get(): Flow<Resume>

    suspend fun set(
        id: Long,
        source: Resume.Source,
    )

    suspend fun setIdAndIndex(
        id: Long,
        index: Int,
    )

    suspend fun setPosition(
        position: Long,
    )
}
