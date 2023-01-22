package com.buggily.enemy.feature.preferences

import com.buggily.enemy.core.model.theme.Theme

data class PreferencesState(
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
            val onSchemeClick: (Theme.Scheme) -> Unit,
        ) {

            companion object {
                val default: SchemeState
                    get() = SchemeState(
                        scheme = Theme.Scheme.Default,
                        schemes = emptyList(),
                        onSchemeClick = {},
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
            val default: ThemeState
                get() = ThemeState(
                    schemeState = SchemeState.default,
                    dynamicState = DynamicState.default,
                    resetState = ResetState.default,
                )
        }
    }

    companion object {
        val default: PreferencesState
            get() = PreferencesState(
                themeState = ThemeState.default,
            )
    }
}
