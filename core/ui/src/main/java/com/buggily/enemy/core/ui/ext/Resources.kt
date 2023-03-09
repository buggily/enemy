package com.buggily.enemy.core.ui.ext

import androidx.annotation.DimenRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.res.ResourcesCompat

@Composable
fun floatResource(@DimenRes id: Int): Float = ResourcesCompat.getFloat(
    LocalContext.current.resources,
    id
)
