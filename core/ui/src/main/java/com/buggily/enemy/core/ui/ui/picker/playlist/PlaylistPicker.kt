package com.buggily.enemy.core.ui.ui.picker.playlist

import com.buggily.enemy.core.ui.R
import com.buggily.enemy.core.ui.ui.picker.Pickerable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface PlaylistPicker : Pickerable {

    @Parcelize
    data object Edit : PlaylistPicker {

        override val stringResId: Int
            get() = R.string.edit_playlist

        override val painterResId: Int
            get() = R.drawable.edit

        override val contentDescriptionResId: Int
            get() = R.string.edit_playlist
    }

    @Parcelize
    data object Delete : PlaylistPicker {

        override val stringResId: Int
            get() = R.string.delete_playlist

        override val painterResId: Int
            get() = R.drawable.delete

        override val contentDescriptionResId: Int
            get() = R.string.delete_playlist
    }

    companion object {
        val values: List<PlaylistPicker> = listOf(
            Edit,
            Delete,
        )
    }
}
