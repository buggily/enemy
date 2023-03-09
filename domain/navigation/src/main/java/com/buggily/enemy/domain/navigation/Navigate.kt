package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationOrchestratable

class Navigate(
    private val orchestrator: NavigationOrchestratable,
) {

    operator fun invoke(
        args: NavigationArgs,
    ) = orchestrator.navigate(
        args = args,
    )
}
