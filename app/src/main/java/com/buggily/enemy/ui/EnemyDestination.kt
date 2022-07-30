package com.buggily.enemy.ui

sealed class EnemyDestination {

    abstract val name: String

    object Default : EnemyDestination() {

        override val name: String
            get() = "default"
    }
}
