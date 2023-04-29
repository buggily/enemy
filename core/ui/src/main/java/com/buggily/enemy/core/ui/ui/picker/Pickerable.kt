package com.buggily.enemy.core.ui.ui.picker

import android.os.Parcelable

interface Pickerable : Parcelable {
    val stringResId: Int
    val painterResId: Int
    val contentDescriptionResId: Int
}
