package com.buggily.enemy.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController

@Composable
fun rememberEnemyAppState(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
): EnemyAppState = remember(
    navController,
    windowSizeClass,
) {
    EnemyAppState(
        navController = navController,
        windowSizeClass = windowSizeClass,
    )
}
