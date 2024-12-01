package com.buggily.enemy.feature.preferences

import com.buggily.enemy.domain.theme.ThemeUi

data class PreferencesUiState(
    val themeState: ThemeState,
    val recentsState: RecentsState,
) {

    data class ThemeState(
        val schemeState: SchemeState,
        val dynamicState: DynamicState,
        val onResetClick: () -> Unit,
    ) {

        data class SchemeState(
            val scheme: ThemeUi.Scheme,
            val schemes: List<ThemeUi.Scheme>,
            val onClick: (ThemeUi.Scheme) -> Unit,
        )

        data class DynamicState(
            val dynamic: ThemeUi.Dynamic,
            val onCheck: (Boolean) -> Unit,
        )
    }

    data class RecentsState(
        val onResetClick: () -> Unit,
    )
}
