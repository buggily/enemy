package com.buggily.enemy.feature.preferences

import com.buggily.enemy.data.theme.Theme

data class PreferencesUiState(
    val themeState: ThemeState,
) {

    data class ThemeState(
        val schemeState: SchemeState,
        val dynamicState: DynamicState,
        val resetState: ResetState,
    ) {

        data class SchemeState(
            val scheme: Theme.Scheme,
            val schemes: List<Theme.Scheme>,
            val onClick: (Theme.Scheme) -> Unit,
        )

        data class DynamicState(
            val dynamic: Theme.Dynamic,
            val onCheck: (Boolean) -> Unit,
        )

        data class ResetState(
            val onClick: () -> Unit,
        )
    }
}
