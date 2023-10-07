package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs

class NavigateBack(
    private val navigate: Navigate,
) {

    operator fun invoke() = navigate(NavigationArgs.Back.WithoutOptions)
}
