package com.buggily.enemy.data.resume

import com.buggily.enemy.local.resume.LocalResumeSourceable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ResumeRepository(
    private val localResumeSource: LocalResumeSourceable,
) : ResumeRepositable {

    override fun get(): Flow<Resume> = localResumeSource.get().map {
        it.to()
    }

    override suspend fun set(
        id: Long,
        source: Resume.Source,
    ) = localResumeSource.set(
        id = id,
        index = source.index,
        source = source.toLocal(),
    )

    override suspend fun setIdAndIndex(
        id: Long,
        index: Int,
    ) = localResumeSource.setIdAndIndex(
        id = id,
        index = index,
    )

    override suspend fun setPosition(
        position: Long,
    ) = localResumeSource.setPosition(
        position = position,
    )
}
