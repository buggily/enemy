package com.buggily.enemy.feature.playlist.track

import com.buggily.enemy.core.ui.R
import com.buggily.enemy.core.ui.ui.picker.Pickerable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface PlaylistTrackPicker : Pickerable {

    @Parcelize
    data object Remove : PlaylistTrackPicker {

        override val stringResId: Int
            get() = R.string.remove_playlist_track

        override val painterResId: Int
            get() = R.drawable.remove

        override val contentDescriptionResId: Int
            get() = R.string.remove_playlist_track
    }

    companion object {
        val values: List<PlaylistTrackPicker> = listOf(Remove)
    }
}
