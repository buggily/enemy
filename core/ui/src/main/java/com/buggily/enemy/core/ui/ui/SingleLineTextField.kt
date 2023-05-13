package com.buggily.enemy.core.ui.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SingleLineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = label,
        placeholder = placeholder,
        trailingIcon = trailingIcon,
        colors = colors,
        modifier = modifier,
    )
}
