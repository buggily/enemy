package com.buggily.enemy.ui.theme

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun EnemyTheme(
    palette: EnemyPalette,
    content: @Composable () -> Unit,
) {
    val context: Context = LocalContext.current
    val colorScheme: ColorScheme = palette.getColorScheme(context)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
    ) {
        Surface(
            content = content,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
