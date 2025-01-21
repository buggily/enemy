package com.buggily.enemy.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontLoadingStrategy
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.buggily.enemy.core.ui.R

private val productSans = FontFamily(
    Font(
        resId = R.font.product_sans,
        weight = FontWeight.Normal,
        style = FontStyle.Normal,
        loadingStrategy = FontLoadingStrategy.Async,
    ),
    Font(
        resId = R.font.product_sans_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal,
        loadingStrategy = FontLoadingStrategy.Async,
    ),
    Font(
        resId = R.font.product_sans_bold_italic,
        weight = FontWeight.Bold,
        style = FontStyle.Italic,
        loadingStrategy = FontLoadingStrategy.Async,
    ),
    Font(
        resId = R.font.product_sans_italic,
        weight = FontWeight.Normal,
        style = FontStyle.Italic,
        loadingStrategy = FontLoadingStrategy.Async,
    ),
)

val Typography: Typography = with(Typography()) {
    copy(
        displayLarge = displayLarge.copy(fontFamily = productSans),
        displayMedium = displayMedium.copy(fontFamily = productSans),
        displaySmall = displaySmall.copy(fontFamily = productSans),
        headlineLarge = headlineLarge.copy(fontFamily = productSans),
        headlineMedium = headlineMedium.copy(fontFamily = productSans),
        headlineSmall = headlineSmall.copy(fontFamily = productSans),
        titleLarge = titleLarge.copy(fontFamily = productSans),
        titleMedium = titleMedium.copy(fontFamily = productSans),
        titleSmall = titleSmall.copy(fontFamily = productSans),
        bodyLarge = bodyLarge.copy(fontFamily = productSans),
        bodyMedium = bodyMedium.copy(fontFamily = productSans),
        bodySmall = bodySmall.copy(fontFamily = productSans),
        labelLarge = labelLarge.copy(fontFamily = productSans),
        labelMedium = labelMedium.copy(fontFamily = productSans),
        labelSmall = labelSmall.copy(fontFamily = productSans),
    )
}
