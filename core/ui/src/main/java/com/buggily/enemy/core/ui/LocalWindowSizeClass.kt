package com.buggily.enemy.core.ui

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.DpSize

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
val LocalWindowSizeClass: ProvidableCompositionLocal<WindowSizeClass> = compositionLocalOf {
    WindowSizeClass.calculateFromSize(DpSize.Unspecified)
}
