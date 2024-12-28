package com.buggily.enemy.domain.resume

import com.buggily.enemy.data.resume.ResumeRepositable
import kotlinx.coroutines.flow.firstOrNull

class GetResume(
    private val resumeRepository: ResumeRepositable,
) {

    suspend operator fun invoke(): ResumeUi? = resumeRepository.get().firstOrNull()?.toUi()
}
