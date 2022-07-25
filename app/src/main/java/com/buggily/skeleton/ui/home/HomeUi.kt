package com.buggily.skeleton.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.buggily.skeleton.ui.SkeletonDestination

@Composable
fun HomeScreen() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SkeletonDestination.Default.name,
        modifier = Modifier.fillMaxSize(),
    ) {

    }
}
