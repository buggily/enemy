package com.buggily.enemy.ui.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import com.buggily.enemy.ui.EnemyState

@Composable
fun rememberEnemyState(
    navController: NavHostController,
    viewModelStoreOwner: ViewModelStoreOwner,
) = remember(
    navController,
    viewModelStoreOwner,
) {
    EnemyState(
        navController = navController,
        activityViewModelStoreOwner = viewModelStoreOwner,
    )
}
