package com.buggily.enemy.domain.resume

import com.buggily.enemy.data.resume.ResumeRepositable

class SetResumePosition(
    private val resumeRepository: ResumeRepositable,
) {

    suspend operator fun invoke(
        position: Long,
    ) = resumeRepository.setPosition(
        position = position,
    )
}
