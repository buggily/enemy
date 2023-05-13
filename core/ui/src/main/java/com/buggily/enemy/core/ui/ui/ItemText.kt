package com.buggily.enemy.core.ui.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import com.buggily.enemy.core.ui.R
import com.buggily.enemy.core.ui.ext.floatResource

@Composable
fun ItemTextMajor(
    text: String,
    textAlign: TextAlign,
    modifier: Modifier = Modifier,
) {
    ItemText(
        text = text,
        textAlign = textAlign,
        modifier = modifier,
    )
}

@Composable
fun ItemTextMinor(
    text: String,
    textAlign: TextAlign,
    modifier: Modifier = Modifier,
) {
    ItemText(
        text = text,
        textAlign = textAlign,
        modifier = modifier.alpha(floatResource(R.dimen.alpha_medium)),
    )
}

@Composable
private fun ItemText(
    text: String,
    textAlign: TextAlign,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        textAlign = textAlign,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier,
    )
}
