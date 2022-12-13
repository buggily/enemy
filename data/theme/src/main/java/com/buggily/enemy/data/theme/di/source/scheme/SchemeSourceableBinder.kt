package com.buggily.enemy.data.theme.di.source.scheme

import com.buggily.enemy.data.theme.source.scheme.SchemeSource
import com.buggily.enemy.data.theme.source.scheme.SchemeSourceable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SchemeSourceableBinder {

    @Binds
    fun binds(
        source: SchemeSource,
    ): SchemeSourceable
}
