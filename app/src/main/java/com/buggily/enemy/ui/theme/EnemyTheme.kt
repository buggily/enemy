package com.buggily.enemy.ui.theme

import android.content.Context
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun EnemyTheme(
    palette: EnemyPalette,
    content: @Composable () -> Unit,
) {
    val context: Context = LocalContext.current
    val colorScheme: ColorScheme = remember(palette) { palette.getColorScheme(context) }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}
