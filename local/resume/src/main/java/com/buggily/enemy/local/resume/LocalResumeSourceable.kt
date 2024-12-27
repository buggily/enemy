package com.buggily.enemy.local.resume

import kotlinx.coroutines.flow.Flow

interface LocalResumeSourceable {

    fun get(): Flow<LocalResume>

    suspend fun set(
        id: Long,
        index: Int,
        source: LocalResume.Source,
    )

    suspend fun setIdAndIndex(
        id: Long,
        index: Int,
    )

    suspend fun setPosition(
        position: Long,
    )
}
