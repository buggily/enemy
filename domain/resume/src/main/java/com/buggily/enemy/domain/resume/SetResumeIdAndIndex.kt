package com.buggily.enemy.domain.resume

import com.buggily.enemy.data.resume.ResumeRepositable

class SetResumeIdAndIndex(
    private val resumeRepository: ResumeRepositable,
) {

    suspend operator fun invoke(
        id: Long,
        index: Int,
    ) = resumeRepository.setIdAndIndex(
        id = id,
        index = index,
    )
}
