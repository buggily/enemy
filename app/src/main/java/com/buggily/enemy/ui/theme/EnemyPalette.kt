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

    sealed class Theme(
        open val isDynamic: Boolean,
    ) {

        val isLight: Boolean
            get() = when (this) {
                is Default -> isSystemInLightTheme
                is Light -> true
                is Dark -> false
            }

        class Default(
            override val isDynamic: Boolean,
            private val isSystemInDarkTheme: Boolean,
        ) : Theme(
            isDynamic = isDynamic,
        ) {

            val isSystemInLightTheme: Boolean
                get() = !isSystemInDarkTheme
        }

        data class Light(
            override val isDynamic: Boolean,
        ) : Theme(
            isDynamic = isDynamic,
        )

        data class Dark(
            override val isDynamic: Boolean,
        ) : Theme(
            isDynamic = isDynamic,
        )
    }

    fun getColorScheme(context: Context): ColorScheme = if (isLight) {
        getLightColorScheme(context)
    } else {
        getDarkColorScheme(context)
    }

    private fun getLightColorScheme(context: Context): ColorScheme = if (isDynamic) {
        dynamicLightColorScheme(context)
    } else {
        lightColorScheme()
    }

    private fun getDarkColorScheme(context: Context): ColorScheme = if (isDynamic) {
        dynamicDarkColorScheme(context)
    } else {
        darkColorScheme()
    }

    val isLight: Boolean
        get() = theme.isLight

    private val isDynamic: Boolean
        get() = theme.isDynamic
}
