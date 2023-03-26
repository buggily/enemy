package com.buggily.enemy.data.theme.di.source.scheme

import com.buggily.enemy.data.theme.source.scheme.SchemeLocalSource
import com.buggily.enemy.data.theme.source.scheme.SchemeLocalSourceable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SchemeLocalSourceableBinder {

    @Binds
    fun binds(
        source: SchemeLocalSource,
    ): SchemeLocalSourceable
}
