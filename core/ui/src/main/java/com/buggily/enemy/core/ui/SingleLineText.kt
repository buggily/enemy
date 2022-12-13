package com.buggily.enemy.core.ui

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun SingleLineText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    textAlign: TextAlign? = null,
) {
    Text(
        text = text,
        style = style,
        textAlign = textAlign,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}
