package com.buggily.enemy.ui.theme

import android.content.Context
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

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
        lightColorScheme
    }

    private fun getDarkColorScheme(context: Context): ColorScheme = if (isDynamic) {
        dynamicDarkColorScheme(context)
    } else {
        darkColorScheme
    }

    val isLight: Boolean
        get() = theme.isLight

    private val isDynamic: Boolean
        get() = theme.isDynamic

    private val lightColorScheme: ColorScheme
        get() = lightColorScheme(
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = onPrimaryContainer,
            inversePrimary = inversePrimary,
            secondary = secondary,
            onSecondary = onSecondary,
            secondaryContainer = secondaryContainer,
            onSecondaryContainer = onSecondaryContainer,
            tertiary = tertiary,
            onTertiary = onTertiary,
            tertiaryContainer = tertiaryContainer,
            onTertiaryContainer = onTertiaryContainer,
            background = background,
            onBackground = onBackground,
            surface = surface,
            onSurface = onSurface,
            surfaceVariant = surfaceVariant,
            onSurfaceVariant = onSurfaceVariant,
            surfaceTint = surfaceTint,
            inverseSurface = inverseSurface,
            inverseOnSurface = inverseOnSurface,
            error = error,
            onError = onError,
            errorContainer = errorContainer,
            onErrorContainer = onErrorContainer,
            outline = outline,
            outlineVariant = outlineVariant,
            scrim = scrim,
        )

    private val darkColorScheme: ColorScheme
        get() = darkColorScheme(
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = onPrimaryContainer,
            inversePrimary = inversePrimary,
            secondary = secondary,
            onSecondary = onSecondary,
            secondaryContainer = secondaryContainer,
            onSecondaryContainer = onSecondaryContainer,
            tertiary = tertiary,
            onTertiary = onTertiary,
            tertiaryContainer = tertiaryContainer,
            onTertiaryContainer = onTertiaryContainer,
            background = background,
            onBackground = onBackground,
            surface = surface,
            onSurface = onSurface,
            surfaceVariant = surfaceVariant,
            onSurfaceVariant = onSurfaceVariant,
            surfaceTint = surfaceTint,
            inverseSurface = inverseSurface,
            inverseOnSurface = inverseOnSurface,
            error = error,
            onError = onError,
            errorContainer = errorContainer,
            onErrorContainer = onErrorContainer,
            outline = outline,
            outlineVariant = outlineVariant,
            scrim = scrim,
        )

    private val primary: Color
        get() = onColor

    private val onPrimary: Color
        get() = getOn(primary)

    private val primaryContainer: Color
        get() = primary

    private val onPrimaryContainer: Color
        get() = getOn(primaryContainer)

    private val inversePrimary: Color
        get() = color

    private val secondary: Color
        get() = onOffColor

    private val onSecondary: Color
        get() = getOnOff(secondary)

    private val secondaryContainer: Color
        get() = secondary

    private val onSecondaryContainer: Color
        get() = getOn(secondaryContainer)

    private val tertiary: Color
        get() = secondary

    private val onTertiary: Color
        get() = getOn(tertiary)

    private val tertiaryContainer: Color
        get() = secondaryContainer

    private val onTertiaryContainer: Color
        get() = getOn(tertiaryContainer)

    private val background: Color
        get() = color

    private val onBackground: Color
        get() = getOn(background)

    private val surface: Color
        get() = offColor

    private val onSurface: Color
        get() = getOnOff(surface)

    private val surfaceVariant: Color
        get() = surface

    private val onSurfaceVariant: Color
        get() = getOn(surfaceVariant)

    private val surfaceTint: Color
        get() = color

    private val inverseSurface: Color
        get() = onOffColor

    private val inverseOnSurface: Color
        get() = getOn(inverseSurface)

    private val error: Color
        get() = Error

    private val onError: Color
        get() = getOn(error)

    private val errorContainer: Color
        get() = error

    private val onErrorContainer: Color
        get() = getOn(errorContainer)

    private val outline: Color
        get() = onColor

    private val outlineVariant: Color
        get() = onOffColor

    private val scrim: Color
        get() = color

    private val color: Color
        get() = get(isLight)

    private val offColor: Color
        get() = getOff(isLight)

    private val onColor: Color
        get() = getOn(color)

    private val onOffColor: Color
        get() = getOnOff(offColor)

    private fun getOn(color: Color): Color {
        return get(getIsDark(color))
    }

    private fun getOnOff(color: Color): Color {
        return getOff(getIsDark(color))
    }

    private fun get(isLight: Boolean): Color {
        return if (isLight) Light else Dark
    }

    private fun getOff(isLight: Boolean): Color {
        return if (isLight) OffLight else OffDark
    }

    private fun getIsDark(color: Color): Boolean {
        return color.luminance() <= 1/2f
    }
}
