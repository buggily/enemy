package com.buggily.skeleton.ui.main

import androidx.compose.runtime.Composable
import com.buggily.skeleton.ui.SkeletonTheme
import com.buggily.skeleton.ui.home.HomeScreen

@Composable
fun MainScreen() {
    SkeletonTheme {
        HomeScreen()
    }
}
