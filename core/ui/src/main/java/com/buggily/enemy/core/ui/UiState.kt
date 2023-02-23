package com.buggily.enemy.core.ui

import com.buggily.enemy.core.model.TimeOfDay

data class UiState(
    val greetingState: GreetingState,
    val searchState: SearchState,
    val fabState: FabState,
    val fabEventState: FabEventState,
) {

    data class GreetingState(
        val timeOfDay: TimeOfDay?,
        val isVisible: Boolean,
        val onVisible: () -> Unit,
    ) {

        companion object {
            val default: GreetingState
                get() = GreetingState(
                    timeOfDay = null,
                    isVisible = true,
                    onVisible = {},
                )
        }
    }

    data class SearchState(
        val value: String,
        val isVisible: Boolean,
        val onClick: () -> Unit,
        val onChange: (String) -> Unit,
        val onClear: () -> Unit,
    ) {

        val isEnabled: Boolean
            get() = isVisible

        companion object {
            val default: SearchState
                get() = SearchState(
                    value = String(),
                    isVisible = false,
                    onClick = {},
                    onChange = {},
                    onClear = {},
                )
        }
    }

    data class FabState(
        val onClick: () -> Unit,
    ) {

        companion object {
            val default: FabState
                get() = FabState(
                    onClick = {},
                )
        }
    }

    sealed class FabEventState {

        object Default : FabEventState()

        sealed class Event(
            open val onEvent: () -> Unit,
        ) : FabEventState() {

            data class Click(
                override val onEvent: () -> Unit,
            ) : Event(
                onEvent = onEvent,
            )
        }
    }

    companion object {
        val default: UiState
            get() = UiState(
                greetingState = UiState.GreetingState.default,
                searchState = UiState.SearchState.default,
                fabState = FabState.default,
                fabEventState = FabEventState.Default,
            )
    }
}
