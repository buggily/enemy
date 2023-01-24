package com.buggily.enemy.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.buggily.enemy.core.model.TimeOfDay
import com.buggily.enemy.core.ui.R.string as strings

@Composable
fun GreetingText(
    timeOfDay: TimeOfDay?,
    modifier: Modifier = Modifier,
) {
    val stringResId: Int = when (timeOfDay) {
        is TimeOfDay.Morning -> strings.morning
        is TimeOfDay.Afternoon -> strings.afternoon
        is TimeOfDay.Evening -> strings.evening
        else -> strings.day
    }

    SingleLineText(
        text = stringResource(strings.greeting, stringResource(stringResId)),
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier,
    )
}
