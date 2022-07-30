package com.buggily.enemy.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.buggily.enemy.ui.EnemyDestination

@Composable
fun HomeScreen() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = EnemyDestination.Default.name,
        modifier = Modifier.fillMaxSize(),
    ) {

    }
}
