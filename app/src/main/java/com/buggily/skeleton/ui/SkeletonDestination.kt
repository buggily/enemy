package com.buggily.skeleton.ui

sealed class SkeletonDestination {

    abstract val name: String

    object Default : SkeletonDestination() {

        override val name: String
            get() = "default"
    }
}
