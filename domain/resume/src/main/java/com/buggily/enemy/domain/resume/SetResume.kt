package com.buggily.enemy.domain.resume

import com.buggily.enemy.data.resume.ResumeRepositable

class SetResume(
    private val resumeRepository: ResumeRepositable,
) {

    suspend operator fun invoke(
        id: Long,
        source: ResumeUi.Source,
    ) = resumeRepository.set(
        id = id,
        source = source.to(),
    )
}
