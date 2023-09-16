package com.buggily.enemy.ui.theme

import android.content.Context
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme

data class EnemyPalette(
    private val theme: Theme,
) {

    sealed interface Theme {

        val isDynamic: Boolean
        val isLight: Boolean

        data class Default(
            override val isDynamic: Boolean,
            private val isSystemInDarkTheme: Boolean,
        ) : Theme {

            override val isLight: Boolean
                get() = isSystemInLightTheme

            private val isSystemInLightTheme: Boolean
                get() = !isSystemInDarkTheme
        }

        data class Light(
            override val isDynamic: Boolean,
        ) : Theme {

            override val isLight: Boolean
                get() = true
        }

        data class Dark(
            override val isDynamic: Boolean,
        ) : Theme {

            override val isLight: Boolean
                get() = false
        }
    }

    fun getColorScheme(context: Context): ColorScheme = if (isLight) {
        getLightColorScheme(context)
    } else {
        getDarkColorScheme(context)
    }

    private fun getLightColorScheme(
        context: Context,
    ): ColorScheme = if (isDynamic) {
        dynamicLightColorScheme(context)
    } else {
        lightColorScheme()
    }

    private fun getDarkColorScheme(
        context: Context,
    ): ColorScheme = if (isDynamic) {
        dynamicDarkColorScheme(context)
    } else {
        darkColorScheme()
    }

    private val isLight: Boolean
        get() = theme.isLight

    private val isDynamic: Boolean
        get() = theme.isDynamic
}
