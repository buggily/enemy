package com.buggily.enemy.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontLoadingStrategy
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.buggily.enemy.R

private val ProductSans = FontFamily(
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
        displayLarge = displayLarge.copy(fontFamily = ProductSans),
        displayMedium = displayMedium.copy(fontFamily = ProductSans),
        displaySmall = displaySmall.copy(fontFamily = ProductSans),
        headlineLarge = headlineLarge.copy(fontFamily = ProductSans),
        headlineMedium = headlineMedium.copy(fontFamily = ProductSans),
        headlineSmall = headlineSmall.copy(fontFamily = ProductSans),
        titleLarge = titleLarge.copy(fontFamily = ProductSans),
        titleMedium = titleMedium.copy(fontFamily = ProductSans),
        titleSmall = titleSmall.copy(fontFamily = ProductSans),
        bodyLarge = bodyLarge.copy(fontFamily = ProductSans),
        bodyMedium = bodyMedium.copy(fontFamily = ProductSans),
        bodySmall = bodySmall.copy(fontFamily = ProductSans),
        labelLarge = labelLarge.copy(fontFamily = ProductSans),
        labelMedium = labelMedium.copy(fontFamily = ProductSans),
        labelSmall = labelSmall.copy(fontFamily = ProductSans),
    )
}
