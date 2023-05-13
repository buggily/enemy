package com.buggily.enemy.core.ui.ui.picker.track

import com.buggily.enemy.core.ui.R
import com.buggily.enemy.core.ui.ui.picker.Pickerable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class TrackPicker : Pickerable {

    @Parcelize
    object Playlist : TrackPicker() {

        override val stringResId: Int
            get() = R.string.add_playlist

        override val painterResId: Int
            get() = R.drawable.add

        override val contentDescriptionResId: Int
            get() = R.string.add_playlist
    }

    companion object {
        val values: List<TrackPicker>
            get() = listOf(Playlist)
    }
}
