package com.buggily.enemy.feature.preferences

import com.buggily.enemy.domain.theme.ThemeUi

data class PreferencesUiState(
    val themeState: ThemeState,
) {

    data class ThemeState(
        val schemeState: SchemeState,
        val dynamicState: DynamicState,
        val resetState: ResetState,
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

        data class ResetState(
            val onClick: () -> Unit,
        )
    }
}
