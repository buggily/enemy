package com.buggily.enemy.core.ui.ui.picker

data class PickerUiState(
    val values: List<Pickerable>,
    val onClick: (Pickerable) -> Unit,
)
