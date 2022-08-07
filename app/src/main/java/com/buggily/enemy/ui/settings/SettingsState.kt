package com.buggily.enemy.ui.settings

import com.buggily.enemy.domain.theme.Theme

data class SettingsState(
    val themeState: ThemeState,
    val dynamicState: DynamicState,
    val resetState: ResetState,
) {

    data class ThemeState(
        val theme: Theme,
        val themes: List<Theme>,
        val onThemeClick: (Theme) -> Unit,
    ) {

        companion object {
            val default: ThemeState
                get() = ThemeState(
                    theme = Theme.Default,
                    themes = emptyList(),
                    onThemeClick = {},
                )
        }
    }

    data class DynamicState(
        val dynamic: Theme.Dynamic,
        val onDynamicCheck: (Boolean) -> Unit,
    ) {

        companion object {
            val default: DynamicState
                get() = DynamicState(
                    dynamic = Theme.Dynamic.Default,
                    onDynamicCheck = {},
                )
        }
    }

    data class ResetState(
        val onResetClick: () -> Unit,
    ) {

        companion object {
            val default: ResetState
                get() = ResetState(
                    onResetClick = {},
                )
        }
    }

    companion object {
        val default: SettingsState
            get() = SettingsState(
                themeState = ThemeState.default,
                dynamicState = DynamicState.default,
                resetState = ResetState.default,
            )
    }
}
