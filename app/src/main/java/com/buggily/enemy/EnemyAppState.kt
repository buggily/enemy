package com.buggily.enemy

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope

class EnemyAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
)
