package com.buggily.enemy.core.ui

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TextButton(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    textAlign: TextAlign? = null,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        SingleLineText(
            text = text,
            style = style,
            textAlign = textAlign,
        )
    }
}
