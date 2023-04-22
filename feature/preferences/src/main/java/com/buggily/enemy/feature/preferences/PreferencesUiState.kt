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
        ) {

            companion object {
                val default: SchemeState
                    get() = SchemeState(
                        scheme = Theme.Scheme.Default,
                        schemes = emptyList(),
                        onClick = {},
                    )
            }
        }

        data class DynamicState(
            val dynamic: Theme.Dynamic,
            val onCheck: (Boolean) -> Unit,
        ) {

            companion object {
                val default: DynamicState
                    get() = DynamicState(
                        dynamic = Theme.Dynamic.Off,
                        onCheck = {},
                    )
            }
        }

        data class ResetState(
            val onClick: () -> Unit,
        ) {

            companion object {
                val default: ResetState
                    get() = ResetState(
                        onClick = {},
                    )
            }
        }

        companion object {
            val default: ThemeState
                get() = ThemeState(
                    schemeState = SchemeState.default,
                    dynamicState = DynamicState.default,
                    resetState = ResetState.default,
                )
        }
    }

    companion object {
        val default: PreferencesUiState
            get() = PreferencesUiState(
                themeState = ThemeState.default,
            )
    }
}
